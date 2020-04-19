package edu.uco.cs.group3_spring2020.kwic.frontend.action.hooks;

import java.util.Set;

import org.json.JSONException;

import edu.uco.cs.group3_spring2020.kwic.api.Entry;

/**
 * A hook to set the searchable content.
 * 
 * @author Caleb L. Power
 */
public interface SetContentHook {
  
  /**
   * Posts some content to be processed.
   * 
   * @param entries the line-by-line input in a JSON array of strings
   * @return boolean <code>true</code> if dispatch was successful
   * @throws JSONException if the user input is bad
   */
  public boolean dispatchNewContent(Set<Entry> entries) throws JSONException;
  
}
