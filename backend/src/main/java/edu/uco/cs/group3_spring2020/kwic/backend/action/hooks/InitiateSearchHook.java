package edu.uco.cs.group3_spring2020.kwic.backend.action.hooks;

import org.json.JSONException;

import edu.uco.cs.group3_spring2020.kwic.api.message.SearchRequest;

/**
 * A hook to act as the interface between the API and the KWIC pipes.
 * 
 * @author Caleb L. Power
 */
public interface InitiateSearchHook {
  
  /**
   * Posts some content to be processed.
   * 
   * @param request the line-by-line input in a JSON array of strings
   * @throws JSONException if the user input is bad
   */
  public void search(SearchRequest request) throws JSONException;
  
}
