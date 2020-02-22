package edu.uco.cs.group3_spring2020.kwic.action.endpoint;

import java.util.HashMap;

import edu.uco.cs.group3_spring2020.kwic.action.HTTPMethod;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

/**
 * Demo page that will be viewed when accessing the web root.
 * 
 * @author Caleb L. Power
 */
public class DemoEndpoint extends Endpoint {

  /**
   * Null constructor to initialize the index page and set the appropriate HTTP request type.
   */
  public DemoEndpoint() {
    super("/", HTTPMethod.GET); //this page should only be accessible via GET
  }

  /**
   * {@inheritDoc}
   */
  @Override public ModelAndView customAction(Request request, Response response) {
    HashMap<String, Object> model = new HashMap<String, Object>() {
      private static final long serialVersionUID = -896727886628790065L; {
        put("titlehttp://marketplace.eclipse.org/marketplace-client-intro?mpc_install=2206168", "Demo Page");
        put("content", "Hello, world!");
    }};

    return new ModelAndView(model, "demopage.ftl"); //use the index template to generate the output
  }

}
