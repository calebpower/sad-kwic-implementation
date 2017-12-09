package com.calebpower.demo.webstandalone.action;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.staticFiles;
import static spark.Spark.stop;

import com.calebpower.demo.webstandalone.action.page.DemoPage;
import com.calebpower.demo.webstandalone.action.page.Page;

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
  private Page pages[] = null; //the pages that will be accessible
  
  /**
   * The request type (limited to GET and/or POST) for a given page.
   * 
   * @author Caleb L. Power
   */
  public static enum RequestType {
    GET_ONLY, POST_ONLY, GET_AND_POST
  }

  /**
   * Opens the specified external port so as to launch the front end.
   * 
   * @param port the port by which the front end will be accessible
   */
  public FrontEnd(int port) {
    
    this.port = port;
    
    if(freeMarkerEngine == null) freeMarkerEngine = new FreeMarkerEngine(RESPONDER_TEMPLATE_FOLDER);
    
    pages = new Page[] {
        new DemoPage()
      };
    
    staticFiles.location(RESPONDER_STATIC_FOLDER); //relative to the root of the classpath
    
  }

  /**
   * Runs the front end in a separate thread so that it can be halted externally.
   */
  @Override public void run() {
    port(port);
    
    for(Page page : pages) { //iterate through initialized pages and determine the appropriate HTTP request types
      
      //pages that need to viewed by URL submission should have HTTP GET enabled
      if(page.getRequestType() == RequestType.GET_ONLY || page.getRequestType() == RequestType.GET_AND_POST) {
        get(page.getRoute(), page::action, freeMarkerEngine);
      }
      
      //pages that need to be viewed by secret form submission (i.e. for passwords) should have HTTP POST enabled
      if(page.getRequestType() == RequestType.POST_ONLY || page.getRequestType() == RequestType.GET_AND_POST) {
        post(page.getRoute(), page::action, freeMarkerEngine);
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
