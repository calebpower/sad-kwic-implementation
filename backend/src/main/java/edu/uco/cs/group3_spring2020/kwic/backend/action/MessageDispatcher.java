package edu.uco.cs.group3_spring2020.kwic.backend.action;

import java.util.Set;
import java.util.UUID;

import com.axonibyte.bonemesh.BoneMesh;

import edu.uco.cs.group3_spring2020.kwic.api.Entry;
import edu.uco.cs.group3_spring2020.kwic.api.message.SearchResponse;
import edu.uco.cs.group3_spring2020.kwic.backend.action.hooks.SearchResponseHook;

/**
 * Dispatches search responses across the network.
 * 
 * @author Caleb L. Power
 */
public class MessageDispatcher implements SearchResponseHook {
  
  private BoneMesh boneMesh = null;
  
  /**
   * Links the BoneMesh engine to the message dispatcher.
   * 
   * @param boneMesh BoneMesh
   */
  public void linkBoneMesh(BoneMesh boneMesh) {
    this.boneMesh = boneMesh;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override public boolean deploySearchResponse(Set<Entry> entries) {
    SearchResponse response = new SearchResponse(UUID.randomUUID(), entries);
    if(boneMesh != null) return boneMesh.broadcastDatum(response, true);
    return false;
  }
  
}
