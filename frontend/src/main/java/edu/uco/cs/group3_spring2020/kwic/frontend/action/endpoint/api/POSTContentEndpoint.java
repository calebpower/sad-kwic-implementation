package edu.uco.cs.group3_spring2020.kwic.frontend.action.endpoint.api;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import edu.uco.cs.group3_spring2020.kwic.api.Entry;
import edu.uco.cs.group3_spring2020.kwic.frontend.KWICFrontend;
import edu.uco.cs.group3_spring2020.kwic.frontend.action.HTTPMethod;
import edu.uco.cs.group3_spring2020.kwic.frontend.action.endpoint.Endpoint;
import edu.uco.cs.group3_spring2020.kwic.frontend.action.hooks.SetContentHook;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

/**
 * Endpoint that accepts content and returns response after pushing through KWIC* workflow.
 * 
 * @author Caleb L. Power
 */
public class POSTContentEndpoint extends Endpoint {
  
  private SetContentHook hook = null;

  /**
   * Overloaded constructor to initialize the index page and set the appropriate HTTP request type.
   */
  public POSTContentEndpoint() {
    super("/api/content", HTTPMethod.POST); //this page should only be accessible via GET
  }
  
  /**
   * Sets the hook required to send the content to the KWIC* system.
   * 
   * @param hook the hook
   * @return this Endpoint object
   */
  public POSTContentEndpoint linkSetContentHook(SetContentHook hook) {
    this.hook = hook;
    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override public ModelAndView customAction(Request request, Response response) {
    KWICFrontend.getLogger().onInfo("ENDPOINT", String.format("User at %1$s hit the POST content endpoint.", request.ip()));
    JSONObject responseBody = new JSONObject();
    
    try {
      JSONObject requestBody = new JSONObject(request.body()); // grab the request
      JSONArray entryArray = requestBody.getJSONArray("entries");
      Set<Entry> entries = new HashSet<>();
      for(Object o : entryArray)
        entries.add(new Entry((JSONObject)o));
      
      if(hook.dispatchNewContent(entries)) { // pass the input to the hook
        responseBody // if we get here, everything's probably going to be okay
            .put("status", "ok")
            .put("info", "Request successful.");
        response.status(202);
      } else {
        responseBody
            .put("status", "error")
            .put("info", "Service unavailable.");
        response.status(503);
      }
    } catch(JSONException e) {
      responseBody // if we get here, then the user (or frontend developer) did not follow the manual
          .put("status", "error")
          .put("info", "Syntax error: " + e.getMessage());
      response.status(400);
      e.printStackTrace();
    } catch(Exception e) {
      responseBody // if we get here, then the backend developer is dumb
          .put("status", "error")
          .put("info", "Internal server error: " + e.getMessage());
      response.status(500);
      e.printStackTrace();
    }
    
    Map<String, Object> model = new HashMap<>() {
      private static final long serialVersionUID = -896727886628790065L; {
        put("body", responseBody.toString(2)); // kick the response back to the user
    }};

    response.header("Content-Type", "application/json");
    return new ModelAndView(model, "raw.ftl"); // use the raw template to generate the output
  }

}
