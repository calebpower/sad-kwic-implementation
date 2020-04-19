package edu.uco.cs.group3_spring2020.kwic.api.message;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.axonibyte.bonemesh.message.GenericMessage;

import edu.uco.cs.group3_spring2020.kwic.api.Entry;

/**
 * Denotes a response to the search request.
 * 
 * @author Caleb L. Power
 */
public class SearchResponse extends GenericMessage {
  
  /**
   * Constructs an error response.
   * 
   * @param from the originating server
   * @param to the recipient
   * @param uuid the tracking ID
   * @param error the error message
   */
  public SearchResponse(String from, String to, UUID uuid, String error) {
    super(from, to, new JSONObject()
        .put("type", MessageType.SEARCH_RESPONSE.name())
        .put("status", "error")
        .put("id", uuid.toString())
        .put("message", error));
  }
  
  /**
   * Constructs a successful search response.
   * Note that search results can be empty.
   * 
   * @param from the originating server
   * @param to the recipient
   * @param uuid the tracking ID
   * @param results the error message
   */
  public SearchResponse(String from, String to, UUID uuid, Set<Entry> results) {
    super(new JSONObject()
        .put("type", MessageType.SEARCH_RESPONSE.name())
        .put("status", "ok")
        .put("id", uuid.toString())
        .put("results", new JSONArray()));
    for(Entry result : results)
      getPayload().getJSONArray("results").put(result.serialize());
  }
  
  /**
   * Constructs a search response from a JSON object.
   * 
   * @param json the JSON Object
   * @throws JSONException if there was an issue parsing the JSON object
   */
  public SearchResponse(JSONObject json) throws JSONException {
    super(json);
  }
  
  /**
   * Determines if the search was successful.
   * 
   * @return <code>true</code> iff the search was successful; the search may
   *         turn up no results and still be successful
   */
  public boolean isOK() {
    return getPayload().getString("status").equals("ok");
  }
  
  /**
   * Retrieves the error message.
   * 
   * @return the error message or <code>null</code> if there was none
   */
  public String getMessage() {
    return getPayload().optString("message");
  }
  
  /**
   * Retrieves the results.
   * 
   * @return a set of results
   */
  public Set<Entry> getResults() {
    if(!getPayload().has("results")) return null;
    Set<Entry> results = new HashSet<>();
    for(Object o : getPayload().getJSONArray("entries"))
      results.add(new Entry((JSONObject)o));
    return results;
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
