package edu.uco.cs.group3_spring2020.kwic.backend.action.hooks;

import java.util.Set;
import java.util.UUID;

import edu.uco.cs.group3_spring2020.kwic.api.Entry;

/**
 * Denotes the search response hook.
 * 
 * @author Caleb L. Power
 */
public interface SearchResponseHook {
  
  /**
   * Deploys the search response message.
   * 
   * @param trackingID the tracking ID
   * @param entries the entry results
   * @return <code>true</code> iff message was dispatched
   */
  public boolean deploySearchResponse(UUID trackingID, Set<Entry> entries);
  
}
