package edu.uco.cs.group3_spring2020.kwic.action;

import static spark.Spark.before;
import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.options;
import static spark.Spark.patch;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.put;
import static spark.Spark.staticFiles;
import static spark.Spark.stop;

import edu.uco.cs.group3_spring2020.kwic.action.endpoint.Endpoint;
import edu.uco.cs.group3_spring2020.kwic.action.endpoint.api.POSTContentEndpoint;
import edu.uco.cs.group3_spring2020.kwic.action.endpoint.page.GETIndexPage;
import edu.uco.cs.group3_spring2020.kwic.action.hooks.PostContentHook;
import edu.uco.cs.group3_spring2020.kwic.domain.controllers.MasterController;

/**
 * Front end view; manages all pages and directs traffic to those pages.
 * 
 * @author Caleb L. Power
 */
public class FrontEnd implements Runnable {
  

  private static final int UI_PORT = 4567;
  private static final String RESPONDER_STATIC_FOLDER = "responder/static";
  private static final String RESPONDER_TEMPLATE_FOLDER = "/responder/templates";
  
  private static FrontEnd frontend = null;
  
  /**
   * Retrieves the only FrontEnd instance.
   * 
   * @return the only instance of the FrontEnd object
   */
  public static FrontEnd getInstance() {
    if(frontend == null) frontend = new FrontEnd();
    return frontend;
  }
  
  private FreeMarkerEngine freeMarkerEngine = null; // the FreeMarker engine
  private Endpoint endpoints[] = null; // the pages that will be accessible
  private PostContentHook postContentHook = null; // the hook used to post content
  
  private FrontEnd() {
    this.postContentHook = new MasterController();
    
    if(freeMarkerEngine == null) freeMarkerEngine = new FreeMarkerEngine(RESPONDER_TEMPLATE_FOLDER);
    
    endpoints = new Endpoint[] {
        new GETIndexPage(this),
        new POSTContentEndpoint(this)
      };
    
    staticFiles.location(RESPONDER_STATIC_FOLDER); // relative to the root of the classpath
  }

  /**
   * Runs the front end in a separate thread so that it can be halted externally.
   */
  @Override public void run() {
    port(UI_PORT);
    
    before((request, response) -> {
      response.header("Access-Control-Allow-Origin", "*");
      response.header("Access-Control-Allow-Methods", "DELETE, POST, GET, PATCH, PUT, OPTIONS");
      response.header("Access-Control-Allow-Headers", "Content-Type, Access-Control-Allow-Headers, Access-Control-Allow-Origin, Access-Control-Allow-Methods");
      response.header("Access-Control-Expose-Headers", "Content-Type, Content-Length");
    });
    
    options("/*", (request, response)-> {
      String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
      if(accessControlRequestHeaders != null)
        response.header("Access-Control-Allow-Headers", "Access-Control-Request-Headers");
      
      String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
      if(accessControlRequestMethod != null)
        response.header("Access-Control-Allow-Methods", "Access-Control-Request-Method");

      return "OK";
    });
    
    for(Endpoint endpoint : endpoints) { // iterate through initialized pages and determine the appropriate HTTP request types
      for(HTTPMethod method : endpoint.getHTTPMethods()) {
        switch(method) {
        case DELETE:
          delete(endpoint.getRoute(), endpoint::action, freeMarkerEngine);
          break;
        case GET:
          get(endpoint.getRoute(), endpoint::action, freeMarkerEngine);
          break;
        case PATCH:
          patch(endpoint.getRoute(), endpoint::action, freeMarkerEngine);
          break;
        case POST:
          post(endpoint.getRoute(), endpoint::action, freeMarkerEngine);
          break;
        case PUT:
          put(endpoint.getRoute(), endpoint::action, freeMarkerEngine);
          break;
        }
      }
    }
  }
  
  /**
   * Stops the web server.
   */
  public void halt() {
    stop();
  }
  
  /**
   * Retrieves the hook used for posting content.
   * 
   * @return the "POST Content" hook
   */
  public PostContentHook getPostContentHook() {
    return postContentHook;
  }
  
}