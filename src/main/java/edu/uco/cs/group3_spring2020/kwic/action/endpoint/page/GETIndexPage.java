package edu.uco.cs.group3_spring2020.kwic.action.endpoint.page;

import java.util.HashMap;

import edu.uco.cs.group3_spring2020.kwic.action.HTTPMethod;
import edu.uco.cs.group3_spring2020.kwic.action.endpoint.Endpoint;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

/**
 * Retrieves the index page.
 * 
 * @author Caleb L. Power
 */
public class GETIndexPage extends Endpoint {

  /**
   * Null constructor to initialize the index page and set the appropriate HTTP request type.
   */
  public GETIndexPage() {
    super("/", HTTPMethod.GET); // this page should only be accessible via GET
  }

  /**
   * {@inheritDoc}
   */
  @Override public ModelAndView customAction(Request request, Response response) {
    System.out.println(String.format("User at %1$s hit the index page.", request.ip()));
    HashMap<String, Object> model = new HashMap<String, Object>() {
      private static final long serialVersionUID = -896727886628790065L; {
        put("title", "Index Page");
        put("content", "Hello, world!");
    }};

    response.header("Content-Type", "text/html");
    return new ModelAndView(model, "index.ftl"); // use the index template to generate the output
  }

}
