package edu.uco.cs.group3_spring2020.kwic.action.endpoint.api;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import edu.uco.cs.group3_spring2020.kwic.action.HTTPMethod;
import edu.uco.cs.group3_spring2020.kwic.action.endpoint.Endpoint;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

/**
 * Endpoint that accepts content and returns response after pushing through KWIC* workflow.
 * 
 * @author Caleb L. Power
 */
public class POSTContentEndpoint extends Endpoint {

  /**
   * Null constructor to initialize the index page and set the appropriate HTTP request type.
   */
  public POSTContentEndpoint() {
    super("/api/content", HTTPMethod.POST); //this page should only be accessible via GET
  }

  /**
   * {@inheritDoc}
   */
  @Override public ModelAndView customAction(Request request, Response response) {
    JSONObject responseBody = new JSONObject();
    
    try {
      JSONObject requestBody = new JSONObject(request.body()); // grab the request
      JSONArray linesInput = requestBody.getJSONArray("lines"); // retrieve the user input array
      
      List<String> lines = new LinkedList<>();
      for(int i = 0; i < linesInput.length(); i++) // validate user input
        lines.add(linesInput.getString(i)); // TODO push this through KWIC implementation
      
      // TODO push this into the pipe, grab response

      try {
        
        // The following is a temporary implementation to kick back the user response.
        
        JSONArray linesOutput = new JSONArray();
        for(String line : lines) linesOutput.put(line); // grab the transformed response and put it into an array
        responseBody // if we get here, everything's probably going to be okay
            .put("status", "ok")
            .put("info", "Request successful.")
            .put("lines", linesOutput);
        response.status(200);
      } catch(Exception e) {
        responseBody // if we get here, then the backend developer is dumb
            .put("status", "error")
            .put("info", "Internal server error: " + e.getMessage());
        response.status(500);
        e.printStackTrace();
      }
      
    } catch(JSONException e) {
      responseBody // if we get here, then the user (or frontend developer) did not follow the manual
          .put("status", "error")
          .put("info", "Syntax error: " + e.getMessage());
      response.status(400);
    }
    
    HashMap<String, Object> model = new HashMap<String, Object>() {
      private static final long serialVersionUID = -896727886628790065L; {
        put("body", responseBody.toString(2)); // kick the response back to the user
    }};

    return new ModelAndView(model, "raw.ftl"); //use the raw template to generate the output
  }

}
