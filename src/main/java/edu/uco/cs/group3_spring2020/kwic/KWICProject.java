package edu.uco.cs.group3_spring2020.kwic;

import edu.uco.cs.group3_spring2020.kwic.action.FrontEnd;
import edu.uco.cs.group3_spring2020.kwic.action.hooks.PostContentHook;
import edu.uco.cs.group3_spring2020.kwic.domain.controllers.MasterController;

/**
 * Engine to index user-provided lines using KWIC* method.
 * 
 * @author Caleb L. Power
 */
public class KWICProject {
  
  private static int UI_PORT = 4567;
  
  private static FrontEnd frontEnd = null; // the front end
  private static PostContentHook masterController = null; // the KWIC* master controller
  
  /**
   * Entry point for the program.
   * 
   * @param args command-line arguments
   */
  public static void main(String[] args) {
    
    System.out.println("Creating KWIC* master controller...");
    masterController = new MasterController();
    
    System.out.println("Launching front end...");
    frontEnd = new FrontEnd(UI_PORT, masterController); // configure the front end
    (new Thread(frontEnd)).start(); // run the front end in a different thread
    
    System.out.println("Ready!");
    
    // catch CTRL + C
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
