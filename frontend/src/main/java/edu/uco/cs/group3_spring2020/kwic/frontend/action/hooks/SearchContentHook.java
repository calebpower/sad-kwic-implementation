package edu.uco.cs.group3_spring2020.kwic.frontend.action.hooks;

import java.util.Set;
import java.util.UUID;

import edu.uco.cs.group3_spring2020.kwic.api.Entry;
import edu.uco.cs.group3_spring2020.kwic.api.message.SearchResponse;

/**
 * A hook to search for content.
 * 
 * @author Caleb L. Power
 */
public interface SearchContentHook {
  
  /**
   * Searches for a particular set of content.
   * 
   * @param keywords the set of keywords
   * @return the tracking ID if dispatch was successful,
   *         or <code>null</code> if not
   */
  public UUID dispatchQuery(Set<String> keywords);
  
  /**
   * Retrieves the results or times out.
   * 
   * @param uuid the tracking ID
   * @param timeout the time we're willing to wait, in seconds
   * @return a set of entries if successful, or <code>null</code> if not
   */
  public Set<Entry> getQueryResults(UUID uuid, int timeout);
  
  /**
   * Triggers a response to a query if one is necessary.
   * 
   * @param response the response
   * @return <code>true</code> iff a response was necessary
   */
  public boolean onQueryResponse(SearchResponse response);
  
}
