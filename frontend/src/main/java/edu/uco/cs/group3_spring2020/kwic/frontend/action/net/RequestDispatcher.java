package edu.uco.cs.group3_spring2020.kwic.frontend.action.net;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

import org.json.JSONException;

import com.axonibyte.bonemesh.BoneMesh;

import edu.uco.cs.group3_spring2020.kwic.api.Entry;
import edu.uco.cs.group3_spring2020.kwic.api.message.SearchRequest;
import edu.uco.cs.group3_spring2020.kwic.api.message.SearchResponse;
import edu.uco.cs.group3_spring2020.kwic.api.message.SetEntriesRequest;
import edu.uco.cs.group3_spring2020.kwic.frontend.KWICFrontend;
import edu.uco.cs.group3_spring2020.kwic.frontend.action.hooks.SearchContentHook;
import edu.uco.cs.group3_spring2020.kwic.frontend.action.hooks.SetContentHook;

/**
 * Dispatches requests and queries across the network.
 * 
 * @author Caleb L. Power
 */
public class RequestDispatcher implements SearchContentHook, SetContentHook {
  
  private BoneMesh boneMesh = null;
  private Set<UUID> pending = null;
  private Map<UUID, Set<Entry>> results = null;
  
  /**
   * Instantiates the request dispatcher.
   */
  public RequestDispatcher() {
    this.results = new ConcurrentHashMap<>();
    this.pending = new CopyOnWriteArraySet<>();
  }
  
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
  @Override public UUID dispatchQuery(Set<String> keywords) {
    UUID uuid = UUID.randomUUID();
    SearchRequest request = new SearchRequest("kwic-frontend", "kwic-backend", uuid, keywords);
    if(!pending.contains(uuid)) pending.add(uuid);
    KWICFrontend.getLogger().onInfo("REQUEST_DISPATCHER", "Dispatching query for " + uuid.toString());
    if(boneMesh != null && boneMesh.sendDatum("kwic-backend", request)) return uuid;
    if(pending.contains(uuid)) pending.remove(uuid);
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override public Set<Entry> getQueryResults(UUID uuid, int timeout) {
    try {
      int c = 0;
      do {
        if(results.containsKey(uuid)) return results.get(uuid);
        KWICFrontend.getLogger().onDebug("REQUEST_DISPATCHER", "Waiting for response to " + uuid.toString());
        Thread.sleep(1000L);
      } while(++c < timeout);
    } catch(InterruptedException e) { }
    
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override public boolean onQueryResponse(SearchResponse response) {
    UUID uuid = response.getID();
    if(results.containsKey(uuid)) results.remove(uuid);
    if(!pending.contains(uuid)) return false;
    pending.remove(uuid);
    KWICFrontend.getLogger().onInfo("REQUEST_DISPATCHER", "Got response to " + uuid.toString());
    results.put(uuid, response.getResults());
    return true;
  }

  /**
   * {@inheritDoc}
   */
  @Override public boolean dispatchNewContent(Set<Entry> entries) throws JSONException {
    SetEntriesRequest request = new SetEntriesRequest("kwic-frontend", "kwic-backend", entries);
    return boneMesh != null && boneMesh.sendDatum("kwic-backend", request);
  }

}
