package com.calebpower.demo.webstandalone.domain;

import com.calebpower.demo.webstandalone.action.FrontEnd;
import com.calebpower.demo.webstandalone.action.Prompter;
import com.calebpower.demo.webstandalone.domain.persistent.Config;

/**
 * Demonstration of a stand-alone web application that utilizes a JSON-based
 * configuration file, the SparkJava microframework, a custom console prompter,
 * the FreeMarker template engine, and JUnit testing.
 * 
 * @author Caleb L. Power
 */
public class Core {
  
  private static Config config = null; //configurations and settings
  private static FrontEnd frontEnd = null; //the front end
  private static Prompter prompter = null; //console prompter
  
  public static void main(String[] args) {
    config = args.length == 1 ? new Config(args[0]).load() : null; //load the config
    if(config == null) { //complain if the config can't be loaded
      System.out.println("Configuration file could not be loaded.");
      System.exit(1); //exit with the proper error code
    }
    
    System.out.println("Launching front end...");
    frontEnd = new FrontEnd(config.getSparkPort()); //configure the front end
    (new Thread(frontEnd)).run(); //run the front end in a different thread
    
    System.out.println("Loading prompter...");
    prompter = new Prompter(); //set up the prompter
    String userResponse = null;
    do { //run the prompter in the main thread
      userResponse = prompter.poll();
    } while(userResponse == null || !userResponse.equalsIgnoreCase("stop"));
    
    System.out.println("Closing front end...");
    frontEnd.halt(); //kill the front end when it's time
    
    System.out.println("Goodbye!"); //:)
  }

}
