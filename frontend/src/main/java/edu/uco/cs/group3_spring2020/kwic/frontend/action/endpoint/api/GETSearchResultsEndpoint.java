package edu.uco.cs.group3_spring2020.kwic.frontend.action.endpoint.api;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;

import edu.uco.cs.group3_spring2020.kwic.api.Entry;
import edu.uco.cs.group3_spring2020.kwic.frontend.action.HTTPMethod;
import edu.uco.cs.group3_spring2020.kwic.frontend.action.endpoint.Endpoint;
import edu.uco.cs.group3_spring2020.kwic.frontend.action.hooks.SearchContentHook;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class GETSearchResultsEndpoint extends Endpoint {
  
  private SearchContentHook hook = null;
  
  public GETSearchResultsEndpoint() {
    super("/api/content", HTTPMethod.GET);
  }
  
  public GETSearchResultsEndpoint linkSearchContentHook(SearchContentHook hook) {
    this.hook = hook;
    return this;
  }

  @Override public ModelAndView customAction(Request request, Response response) {
    System.out.println(String.format("User at %1$s hit the GET content endpoint.", request.ip()));
    JSONObject responseBody = new JSONObject();
    
    try {
    String[] q = request.queryParamsValues("q");
      if(q == null) throw new BadKeywordException();
      
      Set<String> keywords = new HashSet<>();
      for(String kw : q) {
        if(kw.isBlank()) throw new BadKeywordException();
        if(!keywords.contains(kw))
          keywords.add(kw);
      }
      
      if(keywords.size() == 0) throw new BadKeywordException();
      
      UUID trackingUUID = hook.dispatch(keywords);
      if(trackingUUID == null) {
        responseBody
            .put("status", "error")
            .put("info", "Service unavailable.");
        response.status(503);
      } else {
        Set<Entry> results = hook.getResults(trackingUUID, 60);
        if(results == null) {
          responseBody
              .put("status", "error")
              .put("info", "Timed out.");
          response.status(504);
        } else {
          JSONArray keys = new JSONArray();
          for(String kw : keywords)
            keys.put(kw);
          
          JSONArray entries = new JSONArray();
          for(Entry entry : results)
            entries.put(entry.serialize());
          
          responseBody
              .put("status", "ok")
              .put("info", "Query successful.")
              .put("keys", keys)
              .put("entries", entries);
          response.status(200);
        }
      }
      
    } catch(BadKeywordException e) {
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
    
    Map<String, Object> model = new HashMap<>() {
      private static final long serialVersionUID = -1424314212728086392L; {
        put("body", responseBody.toString(2)); // kick the response back to the user
    }};

    response.header("Content-Type", "application/json");
    return new ModelAndView(model, "raw.ftl"); // use the raw template to generate the output
  }
  
  private class BadKeywordException extends Exception {
    private static final long serialVersionUID = 3401390127580662797L;
  }

}
