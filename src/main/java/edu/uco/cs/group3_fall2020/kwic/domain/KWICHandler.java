package edu.uco.cs.group3_fall2020.kwic.domain;

import edu.uco.cs.group3_fall2020.kwic.action.FrontEnd;

/**
 * Demonstration of a stand-alone web application that utilizes a JSON-based
 * configuration file, the SparkJava microframework, a custom console prompter,
 * the FreeMarker template engine, and JUnit testing.
 * 
 * @author Caleb L. Power
 */
public class KWICHandler {
  
  private static int UI_PORT = 4567;
  
  private static FrontEnd frontEnd = null; //the front end
  
  /**
   * Entry point for the program.
   * 
   * @param args command-line arguments
   */
  public static void main(String[] args) {
    
    System.out.println("Launching front end...");
    frontEnd = new FrontEnd(UI_PORT); //configure the front end
    (new Thread(frontEnd)).start(); //run the front end in a different thread
    
    Runtime.getRuntime().addShutdownHook(new Thread() {
      @Override public void run() {
        try {
          System.out.println("Closing front end...");
          frontEnd.halt();
          
          Thread.sleep(1000);
          
          System.out.println("Goodbye!");
          Thread.sleep(200);
        } catch(InterruptedException e) {
          Thread.currentThread().interrupt();
        }
      }
    });
    
  }

}
