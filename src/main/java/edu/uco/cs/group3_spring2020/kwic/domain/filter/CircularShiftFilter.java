package edu.uco.cs.group3_spring2020.kwic.domain.filter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import edu.uco.cs.group3_spring2020.kwic.domain.token.Line;
import edu.uco.cs.group3_spring2020.kwic.domain.token.Word;

/**
 * Returns a set of circularly-shifted lines with reference to a set of words.
 * 
 * @author Caleb L. Power
 */
public class CircularShiftFilter implements Filter {
  
  List<Shifter> shifters = new ArrayList<>();
  Line[] lines = null;
  
  /**
   * Takes individual words as input and returns circularly-shifted lines.
   */
  @Override public Line[] filter(Line[] input) {
    int lineCount = 0;
    for(int i = 0; i < input.length; i++) {
      Shifter shifter = new Shifter(lines, input[i], lineCount);
      lineCount += input[i].getWords().length;
      shifter.thread = new Thread(shifter);
      shifter.thread.setDaemon(true);
      shifter.thread.start();
      shifters.add(shifter);
    }
    
    lines = new Line[lineCount];
    
    try {
      for(;;) {
        for(int i = 0; i < shifters.size(); i++)
          if(shifters.get(i).done.get()) shifters.remove(i);
        if(shifters.size() == 0) break;
        Thread.sleep(100L);
      }
    } catch(InterruptedException e) { }
    
    return lines;
  }
  
  private class Shifter implements Runnable {
    private AtomicBoolean done = new AtomicBoolean(false);
    private List<Shifter> shifters = new ArrayList<>();
    private Thread thread = null;
    private Line line = null;
    private int offset = -1;
    private int shiftFactor = -1;
    
    private Shifter(Line[] lines, Line line, int lineSet) {
      this.offset = lineSet;
      this.line = line;
    }
    
    private Shifter(Line lines[], Line line, int lineSet, int shiftFactor) {
      this.offset = lineSet;
      this.line = line;
      this.shiftFactor = shiftFactor;
    }
    
    @Override public void run() {
      if(shiftFactor == -1) { // coordinate the shifting of a bunch of lines
        for(int i = 0; i < line.getWords().length; i++) {
          Shifter shifter = new Shifter(lines, line, offset, i);
          shifter.thread = new Thread(shifter);
          shifter.thread.setDaemon(true);
          shifter.thread.start();
          shifters.add(shifter);
        }
        
        try {
          for(;;) {
            for(int i = 0; i < shifters.size(); i++)
              if(shifters.get(i).done.get()) shifters.remove(i);
            if(shifters.size() == 0) break;
            Thread.sleep(100L);
          }
          
          done.set(true);
        } catch(InterruptedException e) { }
        
      } else { // coordinate the shifting of a single line
        Word[] words = new Word[line.getWords().length];
        for(int i = 0; i < line.getWords().length; i++)
          words[i] = line.getWords()[(i + shiftFactor) % line.getWords().length];
        Line shiftedLine = new Line(words);
        lines[offset + shiftFactor] = shiftedLine;
        done.set(true);
      }
    }
  }

}
