package edu.uco.cs.group3_spring2020.kwic.domain.state;

/**
 * Receives some input, modified is, and returns some output.
 * 
 * @author Caleb L. Power
 */
public abstract class State {
  
  protected Input input = null;
  
  /**
   * Overloaded constructor to set the input
   * 
   * @param input the input
   */
  public State(Input input) {
    this.input = input;
  }
  
  /**
   * Transforms input.
   */
  public abstract void transform();
  
  /**
   * Retrieves the next state.
   * 
   * @return the next state
   */
  public abstract State nextState();
  
}
