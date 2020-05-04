package edu.uco.cs.group3_spring2020.kwic.action.endpoint;

import edu.uco.cs.group3_spring2020.kwic.action.FrontEnd;
import edu.uco.cs.group3_spring2020.kwic.action.HTTPMethod;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

/**
 * Module abstract class for the easy-adding of custom pages.
 * 
 * @author Caleb L. Power
 */
public abstract class Endpoint {
  
  private FrontEnd frontend = null;
  private HTTPMethod[] methods = null;
  private String route = null;
  
  /**
   * Overloaded constructor to set the request type and the route.
   * 
   * @param frontend the frontend
   * @param methods the type of request allows (GET and/or POST)
   * @param route the public endpoint
   */
  public Endpoint(FrontEnd frontend, String route, HTTPMethod... methods) {
    this.frontend = frontend;
    this.route = route;
    this.methods = methods;
  }
  
  /**
   * Retrieve the HTTP method types for this route.
   * 
   * @return array of type HTTPMethod
   */
  public HTTPMethod[] getHTTPMethods() {
    return methods;
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
  
  /**
   * Retrieves the frontend.
   * 
   * @return the frontend object
   */
  protected FrontEnd getFrontEnd() {
    return frontend;
  }
  
}