package edu.uco.cs.group3_spring2020.kwic.api.message;

import java.util.HashSet;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import com.axonibyte.bonemesh.message.GenericMessage;

import edu.uco.cs.group3_spring2020.kwic.api.Entry;

/**
 * Denotes a request to set the entries.
 * 
 * @author Caleb L. Power
 */
public class SetEntriesRequest extends GenericMessage {
  
  /**
   * Constructs the request from a list of entries.
   * 
   * @param from the originating server
   * @param to the recipient
   * @param entries the entries
   */
  public SetEntriesRequest(String from, String to, Set<Entry> entries) {
    super(from, to, new JSONObject()
        .put("type", MessageType.SET_LINES_REQUEST.name())
        .put("entries", new JSONArray()));
    for(Entry entry : entries)
      getPayload().getJSONArray("entries").put(entry.serialize());
  }
  
  /**
   * Constructs the request from a JSON object.
   * 
   * @param json the JSON object
   */
  public SetEntriesRequest(JSONObject json) {
    super(json);
  }
  
  /**
   * Retrieves the entries.
   * 
   * @return the entries
   */
  public Set<Entry> getEntries() {
    Set<Entry> entries = new HashSet<>();
    for(Object o : getPayload().getJSONArray("entries"))
      entries.add(new Entry((JSONObject)o));
    return entries;
  }
}
