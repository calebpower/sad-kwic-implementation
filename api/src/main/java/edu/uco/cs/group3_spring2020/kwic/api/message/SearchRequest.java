package edu.uco.cs.group3_spring2020.kwic.api.message;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;

import com.axonibyte.bonemesh.message.GenericMessage;

/**
 * Denotes a request to initiate a search.
 * 
 * @author Caleb L. Power
 */
public class SearchRequest extends GenericMessage {
  
  /**
   * Constructs a search request from a tracking ID and the search keys.
   * 
   * @param from the originating server
   * @param to the recipient
   * @param uuid the tracking ID
   * @param keys the keywords
   */
  public SearchRequest(String from, String to, UUID uuid, Set<String> keys) {
    super(from, to, new JSONObject()
        .put("type", MessageType.SEARCH_REQUEST.name())
        .put("id", uuid)
        .put("keys", new JSONArray()));
    for(String key : keys)
      getPayload().getJSONArray("keys").put(key);
  }
  
  /**
   * Constructs a search request from a JSON object.
   * 
   * @param json the JSON object
   */
  public SearchRequest(JSONObject json) {
    super(json);
  }
  
  /**
   * Retrieves the search keys.
   * 
   * @return the keywords
   */
  public List<String> getKeys() {
    List<String> keys = new LinkedList<>();
    for(Object o : getPayload().getJSONArray("keys"))
      keys.add((String)o);
    return keys;
  }
  
  /**
   * Retrieves the tracking ID.
   * 
   * @return the tracking ID
   */
  public UUID getID() {
    if(!getPayload().has("id")) return null;
    return UUID.fromString(getPayload().getString("id"));
  }
 
}
