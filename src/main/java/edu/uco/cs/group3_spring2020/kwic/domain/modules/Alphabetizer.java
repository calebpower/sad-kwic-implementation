package edu.uco.cs.group3_spring2020.kwic.domain.modules;

import java.util.concurrent.atomic.AtomicBoolean;

import edu.uco.cs.group3_spring2020.kwic.domain.token.ComparisonResult;
import edu.uco.cs.group3_spring2020.kwic.domain.token.Line;

/**
 * Filter to alphabetize a list of lines.
 * 
 * @author Caleb L. Power
 */
public class Alphabetizer extends Module {
  
  Line[] lines = null;

  /**
   * Overloaded constructor.
   * 
   * @param input the input
   */
  public Alphabetizer(Input input) {
    super(input);
  }
  
  /**
   * Takes lines and returns them in an ordered fashion.
   */
  @Override public void transform() {
    lines = new Line[input.lines.length];
    Sorter sorter = new Sorter(input.lines, lines, 0, input.lines.length - 1);
    sorter.thread = new Thread(sorter);
    sorter.thread.setDaemon(true);
    sorter.thread.start();
    
    try {
      while(!sorter.done.get()) Thread.sleep(100L);
    } catch(InterruptedException e) { }
    
    input.lines = lines;
  }
  
  private class Sorter implements Runnable {
    
    private AtomicBoolean done = new AtomicBoolean(false);
    private Line[] input = null;
    private Line[] output = null;
    private int leftBound, rightBound;
    private Thread thread = null;
    
    public Sorter(Line[] input, Line[] output, int leftBound, int rightBound) {
      this.input = input;
      this.output = output;
      this.leftBound = leftBound;
      this.rightBound = rightBound;
    }
    
    @Override public void run() {
      if(leftBound < rightBound) { // stop if leftBound == rightBound
        int middle = (rightBound - leftBound) / 2 + leftBound;
        
        // split the dataset
        Sorter leftSorter = new Sorter(input, output, leftBound, middle);
        leftSorter.thread = new Thread(leftSorter);
        leftSorter.thread.setDaemon(true);
        leftSorter.thread.start();
        Sorter rightSorter = new Sorter(input, output, middle + 1, rightBound);
        rightSorter.thread = new Thread(rightSorter);
        rightSorter.thread.setDaemon(true);
        rightSorter.thread.start();
        
        // wait for datasets to finish
        try {
          while(!leftSorter.done.get() || !rightSorter.done.get())
            Thread.sleep(100L);
        } catch(InterruptedException e) { }
        
        // merge the dataset
        int n1 = leftBound;
        int n2 = middle + 1;
        int k = leftBound;
        
        Line[] temp = new Line[rightBound - leftBound + 1];
        for(int i = 0; i <= rightBound - leftBound; i++)
          temp[i] = output[i + leftBound];
        
        while(n1 <= middle && n2 <= rightBound) {
          ComparisonResult comparisonResult = temp[n1 - leftBound].compareTo(temp[n2 - leftBound]);
          switch(comparisonResult) {
          case LESS_THAN:
            output[k++] = temp[n1++ - leftBound];
            break;
          default: // greater than or equal to
            output[k++] = temp[n2++ - leftBound];
            break;
          }
        }
        
        while(n1 <= middle) output[k++] = temp[n1++ - leftBound];
        while(n2 <= rightBound) output[k++] = temp[n2++ - leftBound];
        
      } else output[leftBound] = input[leftBound];
      
      done.set(true);
    }
    
  }

}
