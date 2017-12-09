package com.calebpower.demo.webstandalone.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Simply prompts the console user for a "stop" command and returns
 * whatever they type in the console prompt.
 * 
 * @author Caleb L. Power
 */
public class Prompter {
  
  /**
   * Asks a user to type 'stop' and then returns the user's response.
   * 
   * @return String representation of the user's response or <code>null</code>
   *         if the input stream (console) could not be read
   */
  public String poll() {
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    System.out.println("-- Please enter 'stop' to stop.");
    System.out.print("-> ");
    try {
      return bufferedReader.readLine();
    } catch(IOException e) {
      return null;
    }
  }
  
}
