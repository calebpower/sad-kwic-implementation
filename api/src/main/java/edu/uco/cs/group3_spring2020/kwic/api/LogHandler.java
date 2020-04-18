package edu.uco.cs.group3_spring2020.kwic.api;

import com.axonibyte.bonemesh.listener.cheap.CheapLogListener;

/**
 * Quick implementation of the log listener.
 * 
 * @author Caleb L. Power
 */
public class LogHandler extends CheapLogListener {
  
  /**
   * Dispatches a debugging message. 
   * 
   * @param label the label
   * @param message the message
   */
  public void onDebug(String label, String message) {
    onDebug(label, message, System.currentTimeMillis());
  }
  
  /**
   * Dispatches an informational message.
   * 
   * @param label the label
   * @param message the message
   */
  public void onInfo(String label, String message) {
    onInfo(label, message, System.currentTimeMillis());
  }
  
  /**
   * Dispatches an error message.
   * 
   * @param label the label
   * @param message the message
   */
  public void onError(String label, String message) {
    onError(label, message, System.currentTimeMillis());
  }
  
}
