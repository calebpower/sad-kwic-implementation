package edu.uco.cs.group3_spring2020.kwic.domain.modules;

import edu.uco.cs.group3_spring2020.kwic.domain.token.Line;

/**
 * Receives some input, modified is, and returns some output.
 * 
 * @author Caleb L. Power
 */
public abstract class Module {
  
  protected Input input = null;
  
  /**
   * Overloaded constructor to set the input
   * 
   * @param input the input
   */
  public Module(Input input) {
    this.input = input;
  }
  
  /**
   * Transforms input.
   */
  public abstract void transform();
  
}
