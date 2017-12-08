package com.calebpower.demo.webstandalone.model.page.impl;

import java.util.HashMap;

import com.calebpower.demo.webstandalone.model.page.Page;
import com.calebpower.demo.webstandalone.view.FrontEnd.RequestType;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

/**
 * Demo page that will be viewed when accessing the web root.
 * 
 * @author Caleb L. Power
 */
public class DemoPage extends Page {

  /**
   * Null constructor to initialize the index page and set the appropriate HTTP request type.
   */
  public DemoPage() {
    super(RequestType.GET_ONLY, "/"); //this page should only be accessible via GET
  }

  /**
   * {@inheritDoc}
   */
  @Override public ModelAndView customAction(Request request, Response response) {
    HashMap<String, Object> model = new HashMap<String, Object>() {
      private static final long serialVersionUID = -896727886628790065L; {
        put("title", "Demo Page");
        put("content", "Hello, world!");
    }};

    return new ModelAndView(model, "demopage.ftl"); //use the index template to generate the output
  }

}
