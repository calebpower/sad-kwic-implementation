package edu.uco.cs.group3_spring2020.kwic.backend.domain.modules;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import edu.uco.cs.group3_spring2020.kwic.api.Entry;
import edu.uco.cs.group3_spring2020.kwic.backend.domain.token.Line;
import edu.uco.cs.group3_spring2020.kwic.backend.domain.token.Word;

/**
 * Returns a set of circularly-shifted lines with reference to a set of words.
 * 
 * @author Caleb L. Power
 */
public class CircularShifter extends Module {
  
  List<Shifter> shifters = new ArrayList<>();
  Object[][] lines = null;
  
  /**
   * Overloaded constructor.
   * 
   * @param entries the input
   */
  public CircularShifter(Map<Line, Entry> entries) {
    super(entries);
  }
  
  /**
   * Takes individual words as input and returns circularly-shifted lines.
   */
  @Override public void transform() {
    int lineCount = 0;
    for(Line line : entries.keySet()) {
      Shifter shifter = new Shifter(line, entries.get(line), lineCount);
      lineCount += line.getWords().length;
      shifter.thread = new Thread(shifter);
      shifter.thread.setDaemon(true);
      shifters.add(shifter);
    }
    
    lines = new Object[lineCount][2];
    
    for(Shifter shifter : shifters) shifter.thread.start();
    
    try {
      for(;;) {
        for(int i = 0; i < shifters.size(); i++)
          if(shifters.get(i).done.get()) shifters.remove(i);
        if(shifters.size() == 0) break;
        Thread.sleep(100L);
      }
    } catch(InterruptedException e) { }
    
    entries.clear();
    for(Object[] line : lines)
      entries.put((Line)line[0], (Entry)line[1]);
  }
  
  private class Shifter implements Runnable {
    private AtomicBoolean done = new AtomicBoolean(false);
    private List<Shifter> shifters = new ArrayList<>();
    private Thread thread = null;
    private Line line = null;
    private Entry entry = null;
    private int offset = -1;
    private int shiftFactor = -1;
    
    private Shifter(Line line, Entry entry, int lineSet) {
      this.offset = lineSet;
      this.line = line;
      this.entry = entry;
    }
    
    private Shifter(Line line, Entry entry, int lineSet, int shiftFactor) {
      this.offset = lineSet;
      this.line = line;
      this.entry = entry;
      this.shiftFactor = shiftFactor;
    }
    
    @Override public void run() {
      if(shiftFactor == -1) { // coordinate the shifting of a bunch of lines
        for(int i = 0; i < line.getWords().length; i++) {
          Shifter shifter = new Shifter(line, entry, offset, i);
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
        lines[offset + shiftFactor] = new Object[2];
        lines[offset + shiftFactor][0] = shiftedLine;
        lines[offset + shiftFactor][1] = entry;
        done.set(true);
      }
    }
  }

}
