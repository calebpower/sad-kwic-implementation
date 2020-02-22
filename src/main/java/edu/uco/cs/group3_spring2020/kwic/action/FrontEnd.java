package edu.uco.cs.group3_spring2020.kwic.action;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.patch;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.put;
import static spark.Spark.staticFiles;
import static spark.Spark.stop;

import edu.uco.cs.group3_spring2020.kwic.action.endpoint.DemoEndpoint;
import edu.uco.cs.group3_spring2020.kwic.action.endpoint.Endpoint;

/**
 * Front end view; manages all pages and directs traffic to those pages.
 * 
 * @author Caleb L. Power
 */
public class FrontEnd implements Runnable {
  
  private static final String RESPONDER_STATIC_FOLDER = "responder/static";
  private static final String RESPONDER_TEMPLATE_FOLDER = "/responder/templates";
  
  private int port; //the port that the front end should run on
  private FreeMarkerEngine freeMarkerEngine = null; //the FreeMarker engine
  private Endpoint endpoints[] = null; //the pages that will be accessible
  
  /**
   * Opens the specified external port so as to launch the front end.
   * 
   * @param port the port by which the front end will be accessible
   */
  public FrontEnd(int port) {
    this.port = port;
    
    if(freeMarkerEngine == null) freeMarkerEngine = new FreeMarkerEngine(RESPONDER_TEMPLATE_FOLDER);
    
    endpoints = new Endpoint[] {
        new DemoEndpoint()
      };
    
    staticFiles.location(RESPONDER_STATIC_FOLDER); //relative to the root of the classpath
  }

  /**
   * Runs the front end in a separate thread so that it can be halted externally.
   */
  @Override public void run() {
    port(port);
    
    for(Endpoint endpoint : endpoints) { //iterate through initialized pages and determine the appropriate HTTP request types
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
  
}
