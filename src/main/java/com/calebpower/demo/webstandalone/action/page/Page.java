package com.calebpower.demo.webstandalone.action.page;

import com.calebpower.demo.webstandalone.action.FrontEnd;
import com.calebpower.demo.webstandalone.action.FrontEnd.RequestType;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

/**
 * Module abstract class for the easy-adding of custom pages.
 * 
 * @author Caleb L. Power
 */
public abstract class Page {
  
  private RequestType requestType = null;
  private String route = null;
  
  /**
   * Overloaded constructor to set the request type and the route.
   * 
   * @param requestType
   * @param route
   */
  public Page(RequestType requestType, String route) {
    this.requestType = requestType;
    this.route = route;
  }
  
  /**
   * Retrieve the request type (GET, POST, or BOTH) for the particular module.
   * 
   * @return <code>GET_ONLY</code> if only a GET request is to be used,
   *         <code>POST_ONLY</code> if only a POST request is to be used, and
   *         <code>GET_AND_POST</code> if both a GET and POST request can be used
   */
  public RequestType getRequestType() {
    return requestType;
  }
  
  /**
   * Retrieve the route for the module.
   * 
   * @return String representing the route to be used for the module.
   */
  public String getRoute() {
    return route;
  }
  
  /**
   * The actions that will be carried out for all routes.
   * 
   * @param request REST request
   * @param response REST response
   * @return ModelAndView containing the HTTP response (often in JSON)
   */
  public ModelAndView action(Request request, Response response) {
    return customAction(request, response);
  }
  
  /**
   * The action in question for the particular module.
   * 
   * @param request REST request
   * @param response REST response
   * @return ModelAndView containing the HTTP response (often in JSON)
   */
  public abstract ModelAndView customAction(Request request, Response response);
  
}