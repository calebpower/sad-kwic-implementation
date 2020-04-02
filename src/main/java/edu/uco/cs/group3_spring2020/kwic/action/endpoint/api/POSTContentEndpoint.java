package edu.uco.cs.group3_spring2020.kwic.action.endpoint.api;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import edu.uco.cs.group3_spring2020.kwic.action.HTTPMethod;
import edu.uco.cs.group3_spring2020.kwic.action.endpoint.Endpoint;
import edu.uco.cs.group3_spring2020.kwic.action.hooks.PostContentHook;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

/**
 * Endpoint that accepts content and returns response after pushing through KWIC* workflow.
 * 
 * @author Caleb L. Power
 */
public class POSTContentEndpoint extends Endpoint {
  
  private PostContentHook hook = null;

  /**
   * Overloaded constructor to initialize the index page and set the appropriate HTTP request type.
   * 
   * @param hook the hook that will pipe user input to the KWIC* pipe
   */
  public POSTContentEndpoint(PostContentHook hook) {
    super("/api/content", HTTPMethod.POST); //this page should only be accessible via GET
    this.hook = hook;
  }

  /**
   * {@inheritDoc}
   */
  @Override public ModelAndView customAction(Request request, Response response) {
    System.out.println(String.format("User at %1$s hit the POST content endpoint.", request.ip()));
    JSONObject responseBody = new JSONObject();
    
    try {
      JSONObject requestBody = new JSONObject(request.body()); // grab the request
      JSONArray linesInput = requestBody.getJSONArray("lines"); // retrieve the user input array

      JSONArray linesOutput = hook.dispatch(linesInput); // pass the input to the hook, retrieve the output
      
      responseBody // if we get here, everything's probably going to be okay
          .put("status", "ok")
          .put("info", "Request successful.")
          .put("lines", linesOutput);
      response.status(200);
    } catch(JSONException e) {
      responseBody // if we get here, then the user (or frontend developer) did not follow the manual
          .put("status", "error")
          .put("info", "Syntax error: " + e.getMessage());
      response.status(400);
    } catch(Exception e) {
      responseBody // if we get here, then the backend developer is dumb
          .put("status", "error")
          .put("info", "Internal server error: " + e.getMessage());
      response.status(500);
      e.printStackTrace();
    }
    
    HashMap<String, Object> model = new HashMap<String, Object>() {
      private static final long serialVersionUID = -896727886628790065L; {
        put("body", responseBody.toString(2)); // kick the response back to the user
    }};

    response.header("Content-Type", "application/json");
    return new ModelAndView(model, "raw.ftl"); //use the raw template to generate the output
  }

}
