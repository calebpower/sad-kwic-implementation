package edu.uco.cs.group3_spring2020.kwic.api.message;

/**
 * Denotes a message type.
 * 
 * @author Caleb L. Power
 */
public enum MessageType {
  
  /**
   * Denotes a request to set entries.
   */
  SET_LINES_REQUEST,
  
  /**
   * Denotes a request to search for entries.
   */
  SEARCH_REQUEST,
  
  /**
   * Denotes a response to the search request.
   */
  SEARCH_RESPONSE
  
}
