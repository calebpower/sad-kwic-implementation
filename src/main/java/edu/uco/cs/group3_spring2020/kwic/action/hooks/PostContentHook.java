package edu.uco.cs.group3_spring2020.kwic.action.hooks;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * A hook to act as the interface between the API and the KWIC pipes.
 * 
 * @author Caleb L. Power
 */
public interface PostContentHook {
  
  /**
   * Posts some content to be processed.
   * 
   * @param input the line-by-line input in a JSON array of strings
   * @return the line-by-line output in a JSON array of strings
   * @throws JSONException if the user input is bad
   */
  public JSONArray dispatch(JSONArray input) throws JSONException;
  
}
