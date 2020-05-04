package edu.uco.cs.group3_spring2020.kwic.domain.controllers;

import org.json.JSONArray;
import org.json.JSONException;

import edu.uco.cs.group3_spring2020.kwic.action.hooks.PostContentHook;
import edu.uco.cs.group3_spring2020.kwic.domain.state.CircularShiftState;
import edu.uco.cs.group3_spring2020.kwic.domain.state.Input;
import edu.uco.cs.group3_spring2020.kwic.domain.state.Output;
import edu.uco.cs.group3_spring2020.kwic.domain.state.State;

/**
 * Handles sequencing of other modules
 * 
 * @author Caleb L. Power
 */
public class MasterController implements PostContentHook {
  
  /**
   * {@inheritDoc}
   */
  @Override public Output dispatch(JSONArray dataInput) throws JSONException {
    final long startTime = System.currentTimeMillis();
    Input input = new Input(dataInput);
    
    State state = new CircularShiftState(input);
    while(state != null) {
      state.transform();
      state = state.nextState();
    }
    
    final long stopTime = System.currentTimeMillis();
    System.out.println("Operation completed in " + (stopTime - startTime) + " milliseconds.");
    
    return new Output(input);
  }

}
