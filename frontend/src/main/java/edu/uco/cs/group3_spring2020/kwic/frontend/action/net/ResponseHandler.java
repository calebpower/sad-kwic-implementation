package edu.uco.cs.group3_spring2020.kwic.frontend.action.net;

import org.json.JSONException;
import org.json.JSONObject;

import com.axonibyte.bonemesh.listener.DataListener;

import edu.uco.cs.group3_spring2020.kwic.api.message.MessageType;
import edu.uco.cs.group3_spring2020.kwic.api.message.SearchResponse;
import edu.uco.cs.group3_spring2020.kwic.frontend.action.hooks.SearchContentHook;

/**
 * Handles incoming requests from BoneMesh.
 * 
 * @author Caleb L. Power
 */
public class ResponseHandler implements DataListener {
  
  private SearchContentHook searchContentHook = null;
  
  /**
   * Sets the Search Content hook.
   * 
   * @param hook the hook
   * @return this ResposneHandler object
   */
  public ResponseHandler setSetLinesHook(SearchContentHook hook) {
    this.searchContentHook = hook;
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override public void digest(JSONObject message) {
    try {
      if(message.has("payload") && message.getJSONObject("payload").has("type")) {
        MessageType type = MessageType.valueOf(message.getJSONObject("payload").getString("type"));
        if(type != null) {
          switch(type) {
          case SEARCH_RESPONSE:
            if(searchContentHook == null) System.err.println("Hook to handle search response not found.");
            else searchContentHook.onQueryResponse(new SearchResponse(message));
            break;
          default:
            System.err.printf("This platform was not designed to handle messages of type '%1$s'.", type.toString());
          }
          return;
        }
      }
      
      System.err.println("Message did not have a valid type.");
    } catch(JSONException e) {
      System.err.println("Could not parse incoming message: " + e.getMessage());
    }
  }

}
