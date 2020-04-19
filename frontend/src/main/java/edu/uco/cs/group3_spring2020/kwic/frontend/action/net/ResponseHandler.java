package edu.uco.cs.group3_spring2020.kwic.frontend.action.net;

import org.json.JSONException;
import org.json.JSONObject;

import com.axonibyte.bonemesh.listener.DataListener;

import edu.uco.cs.group3_spring2020.kwic.api.message.MessageType;
import edu.uco.cs.group3_spring2020.kwic.api.message.SearchResponse;
import edu.uco.cs.group3_spring2020.kwic.frontend.KWICFrontend;
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
      if(message.has("payload")
          && message.getJSONObject("payload").has("payload")
          && message.getJSONObject("payload").getJSONObject("payload").has("type")) {
        MessageType type = MessageType.valueOf(message.getJSONObject("payload").getJSONObject("payload").getString("type"));
        if(type != null) {
          switch(type) {
          case SEARCH_RESPONSE:
            if(searchContentHook == null) KWICFrontend.getLogger().onInfo("RESPONSE_HANDLER", "Hook to handle search response not found.");
            else if(searchContentHook.onQueryResponse(new SearchResponse(message.getJSONObject("payload"))))
              KWICFrontend.getLogger().onInfo("RESPONSE_HANDLER", "Successfully posted a query response.");
            else
              KWICFrontend.getLogger().onError("RESPONSE_HANDLER", "Caught a query response we didn't ask for.");
            break;
          default:
            KWICFrontend.getLogger().onError("RESPONSE_HANDLER",
                String.format("This platform was not designed to handle messages of type '%1$s'.", type.toString()));
          }
          return;
        }
      }
      
      KWICFrontend.getLogger().onError("RESPONSE_HANDLER", "Message did not have a valid type.");
    } catch(JSONException e) {
      KWICFrontend.getLogger().onError("RESPONSE_HANDLER", "Could not parse incoming message: " + e.getMessage());
    }
  }

}
