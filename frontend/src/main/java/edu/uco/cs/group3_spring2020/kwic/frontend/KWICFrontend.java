package edu.uco.cs.group3_spring2020.kwic.frontend;

import com.axonibyte.bonemesh.BoneMesh;

import edu.uco.cs.group3_spring2020.kwic.api.LogHandler;
import edu.uco.cs.group3_spring2020.kwic.frontend.action.FrontEnd;
import edu.uco.cs.group3_spring2020.kwic.frontend.action.net.RequestDispatcher;
import edu.uco.cs.group3_spring2020.kwic.frontend.action.net.ResponseHandler;

/**
 * Engine to index user-provided lines using KWIC* method.
 * 
 * @author Caleb L. Power
 */
public class KWICFrontend {
  
  private static final int BONEMESH_PORT = 9568;
  private static final int UI_PORT = 9569;
  
  private static BoneMesh boneMesh = null; // BoneMesh v2
  private static FrontEnd frontEnd = null; // the front end
  private static LogHandler logHandler = null; // the logger
  private static RequestDispatcher requestDispatcher = null; // the request dispatcher
  private static ResponseHandler responseHandler = null; // the incoming response handler
  
  /**
   * Entry point for the program.
   * 
   * @param args command-line arguments
   */
  public static void main(String[] args) {
    logHandler = new LogHandler();
    try {
      logHandler.onInfo("FRONTEND", "Instantiating modules...");
      requestDispatcher = new RequestDispatcher();
      responseHandler = new ResponseHandler();
      frontEnd = new FrontEnd(UI_PORT, requestDispatcher, requestDispatcher);
      
      logHandler.onInfo("FRONTEND", "Enabling network connectivity...");
      boneMesh = BoneMesh.build("kwic-frontend", BONEMESH_PORT);
      boneMesh.addNode("kwic-backend", "127.0.0.1:8457");
      
      logHandler.onInfo("FRONTEND", "Linking modules...");
      boneMesh.addLogListener(logHandler);
      responseHandler.setSetLinesHook(requestDispatcher);
      requestDispatcher.linkBoneMesh(boneMesh);
      boneMesh.addDataListener(responseHandler);
      
      logHandler.onInfo("FRONTEND", "Loading UI...");
      (new Thread(frontEnd)).start(); // run the front end in a different thread
      
      logHandler.onInfo("FRONTEND", "Ready!");
      
      // catch CTRL + C
      Runtime.getRuntime().addShutdownHook(new Thread() {
        @Override public void run() {
          try {
            logHandler.onInfo("FRONTEND", "Spinning down...");
            frontEnd.halt();
            boneMesh.removeNode("kwic-backend");
            boneMesh.removeDataListener(responseHandler);
            boneMesh.removeLogListener(logHandler);
            boneMesh.kill();
            Thread.sleep(1000);
          } catch(InterruptedException e) {
            Thread.currentThread().interrupt();
          } finally {
            logHandler.onInfo("FRONTEND", "Goodbye!");
          }
        }
      });
    
    } catch(Exception e) {
      logHandler.onError("FRONTEND", "Some exception was thrown: " + e.getMessage());
    }
  }
  
  /**
   * Retrieves the logger.
   * 
   * @return the logger 
   */
  public static LogHandler getLogger() {
    return logHandler;
  }
  
  /**
   * Retrieves the label of this BoneMesh node.
   * 
   * @return the label or <code>null</code> if BoneMesh isn't instantiated
   */
  public static String getLabel() {
    return boneMesh == null ? null : boneMesh.getInstanceLabel();
  }

}
