package edu.uco.cs.group3_spring2020.kwic.backend.action;

import org.json.JSONException;
import org.json.JSONObject;

import com.axonibyte.bonemesh.listener.DataListener;

import edu.uco.cs.group3_spring2020.kwic.api.message.MessageType;
import edu.uco.cs.group3_spring2020.kwic.api.message.SearchRequest;
import edu.uco.cs.group3_spring2020.kwic.api.message.SetEntriesRequest;
import edu.uco.cs.group3_spring2020.kwic.backend.KWICBackend;
import edu.uco.cs.group3_spring2020.kwic.backend.action.hooks.InitiateSearchHook;
import edu.uco.cs.group3_spring2020.kwic.backend.action.hooks.SetLinesHook;

/**
 * Handles incoming requests from BoneMesh.
 * 
 * @author Caleb L. Power
 */
public class DataHandler implements DataListener {
  
  private InitiateSearchHook initiateSearchHook = null;
  private SetLinesHook setLinesHook = null;
  
  /**
   * Sets the Initiate Search hook.
   * 
   * @param hook the hook
   * @return this DataHandler object
   */
  public DataHandler setInitiateSearchHook(InitiateSearchHook hook) {
    this.initiateSearchHook = hook;
    return this;
  }
  
  /**
   * Sets the Set Lines hook.
   * 
   * @param hook the hook
   * @return this DataHandler object
   */
  public DataHandler setSetLinesHook(SetLinesHook hook) {
    this.setLinesHook = hook;
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
          case SET_LINES_REQUEST:
            if(setLinesHook == null) KWICBackend.getLogger().onError("DATA_HANDLER", "Hook to set lines not found.");
            else setLinesHook.setEntries(new SetEntriesRequest(message.getJSONObject("payload")));
            break;
          case SEARCH_REQUEST:
            if(initiateSearchHook == null) KWICBackend.getLogger().onError("DATA_HANDLER", "Hook to initiate search not found.");
            else initiateSearchHook.search(new SearchRequest(message.getJSONObject("payload")));
            break;
          default:
            KWICBackend.getLogger().onError("DATA_HANDLER", String.format("This platform was not designed to handle messages of type '%1$s'.", type.toString()));
          }
          return;
        }
      }
      
      KWICBackend.getLogger().onError("DATA_HANDLER", "Message did not have a valid type.");
    } catch(JSONException e) {
      KWICBackend.getLogger().onError("DATA_HANDLER", "Could not parse incoming message: " + e.getMessage());
    }
  }

}
