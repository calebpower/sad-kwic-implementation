package edu.uco.cs.group3_spring2020.kwic.backend;

import com.axonibyte.bonemesh.BoneMesh;

import edu.uco.cs.group3_spring2020.kwic.api.LogHandler;
import edu.uco.cs.group3_spring2020.kwic.backend.action.DataHandler;
import edu.uco.cs.group3_spring2020.kwic.backend.action.MessageDispatcher;
import edu.uco.cs.group3_spring2020.kwic.backend.domain.controllers.LineManager;

/**
 * Engine to index user-provided lines using KWIC* method.
 * 
 * @author Caleb L. Power
 */
public class KWICBackend {
  
  private static final int EXTERNAL_PORT = 8457;
  
  private static BoneMesh boneMesh = null; // BoneMesh v2
  private static DataHandler dataHandler = null; // the incoming data handler
  private static LineManager lineManager = null; // the KWIC* master controller
  private static LogHandler logHandler = null; // the logger
  private static MessageDispatcher messageDispatcher = null; // the message dispatcher
  
  /**
   * Entry point for the program.
   * 
   * @param args command-line arguments
   */
  public static void main(String[] args) {
    
    logHandler = new LogHandler();
    logHandler.onInfo("BACKEND", "Instantiating modules...");
    lineManager = new LineManager();
    dataHandler = new DataHandler();
    messageDispatcher = new MessageDispatcher();
    
    logHandler.onInfo("BACKEND", "Enabling network connectivity...");
    boneMesh = BoneMesh.build("kwic-backend", EXTERNAL_PORT);
    
    logHandler.onInfo("BACKEND", "Linking modules...");
    boneMesh.addLogListener(logHandler);
    messageDispatcher.linkBoneMesh(boneMesh);
    lineManager.setSearchResponseHook(messageDispatcher);
    dataHandler.setInitiateSearchHook(lineManager).setSetLinesHook(lineManager);
    boneMesh.addDataListener(dataHandler);
    
    logHandler.onInfo("BACKEND", "Ready!");

    // catch CTRL + C
    Runtime.getRuntime().addShutdownHook(new Thread() {
      @Override public void run() {
        try {
          logHandler.onInfo("BACKEND", "Spinning down...");
          boneMesh.kill();
          Thread.sleep(1000);
        } catch(InterruptedException e) {
          Thread.currentThread().interrupt();
        } finally {
          logHandler.onInfo("BACKEND", "Goodbye!");
        }
      }
    });
    
    try {
      while(!Thread.interrupted())
        Thread.sleep(1000L);
    } catch(InterruptedException e) { }
    
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
