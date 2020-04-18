package edu.uco.cs.group3_spring2020.kwic.backend.domain.modules;

import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import edu.uco.cs.group3_spring2020.kwic.api.Entry;
import edu.uco.cs.group3_spring2020.kwic.backend.domain.token.ComparisonResult;
import edu.uco.cs.group3_spring2020.kwic.backend.domain.token.Line;

/**
 * Filter to alphabetize a list of lines.
 * 
 * @author Caleb L. Power
 */
public class Alphabetizer extends Module {
  Object[][] input = null;
  Object[][] output = null;

  /**
   * Overloaded constructor.
   * 
   * @param entries the input
   */
  public Alphabetizer(Map<Line, Entry> entries) {
    super(entries);
  }
  
  /**
   * Takes lines and returns them in an ordered fashion.
   */
  @Override public void transform() {
    output = new Object[entries.size()][2];
    input = new Object[entries.size()][2];
    int c = 0;
    for(Line line : entries.keySet()) {
      input[c][0] = line;
      input[c++][1] = entries.get(line);
    }
    
    Sorter sorter = new Sorter(0, input.length - 1);
    sorter.thread = new Thread(sorter);
    sorter.thread.setDaemon(true);
    sorter.thread.start();
    
    try {
      while(!sorter.done.get()) Thread.sleep(100L);
    } catch(InterruptedException e) { }
    
    entries.clear();
    for(Object[] o : output)
      entries.put((Line)o[0], (Entry)o[1]);
  }
  
  private class Sorter implements Runnable {
    
    private AtomicBoolean done = new AtomicBoolean(false);
    private int leftBound, rightBound;
    private Thread thread = null;
    
    public Sorter(int leftBound, int rightBound) {
      this.leftBound = leftBound;
      this.rightBound = rightBound;
    }
    
    @Override public void run() {
      if(leftBound < rightBound) { // stop if leftBound == rightBound
        int middle = (rightBound - leftBound) / 2 + leftBound;
        
        // split the dataset
        Sorter leftSorter = new Sorter(leftBound, middle);
        leftSorter.thread = new Thread(leftSorter);
        leftSorter.thread.setDaemon(true);
        leftSorter.thread.start();
        Sorter rightSorter = new Sorter(middle + 1, rightBound);
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
        
        Object[][] temp = new Line[rightBound - leftBound + 1][2];
        for(int i = 0; i <= rightBound - leftBound; i++)
          temp[i] = output[i + leftBound];
        
        while(n1 <= middle && n2 <= rightBound) {
          ComparisonResult comparisonResult = ((Line)temp[n1 - leftBound][0]).compareTo((Line)temp[n2 - leftBound][0]);
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
