package edu.uco.cs.group3_spring2020.kwic.backend.action;

import java.util.Set;
import java.util.UUID;

import com.axonibyte.bonemesh.BoneMesh;

import edu.uco.cs.group3_spring2020.kwic.api.Entry;
import edu.uco.cs.group3_spring2020.kwic.api.message.SearchResponse;
import edu.uco.cs.group3_spring2020.kwic.backend.KWICBackend;
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
  @Override public boolean deploySearchResponse(UUID trackingID, Set<Entry> entries) {
    SearchResponse response = new SearchResponse("kwic-backend", "kwic-frontend", trackingID, entries);
    if(boneMesh != null) {
      KWICBackend.getLogger().onInfo("MESSAGE_DISPATCHER", "Dispatching response to " + trackingID.toString());
      return boneMesh.sendDatum("kwic-frontend", response, true);
    }
    KWICBackend.getLogger().onError("MESSAGE_DISPATCHER", "BoneMesh not instantiated!");
    return false;
  }
  
}
