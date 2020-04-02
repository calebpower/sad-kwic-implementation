package edu.uco.cs.group3_spring2020.kwic.domain.controllers;

import org.json.JSONArray;
import org.json.JSONException;

import edu.uco.cs.group3_spring2020.kwic.action.hooks.PostContentHook;
import edu.uco.cs.group3_spring2020.kwic.domain.modules.Alphabetizer;
import edu.uco.cs.group3_spring2020.kwic.domain.modules.CircularShift;
import edu.uco.cs.group3_spring2020.kwic.domain.modules.Input;
import edu.uco.cs.group3_spring2020.kwic.domain.modules.Module;
import edu.uco.cs.group3_spring2020.kwic.domain.modules.NoiseRemover;
import edu.uco.cs.group3_spring2020.kwic.domain.modules.Output;

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
    Input input = new Input(dataInput);
    
    final Module[] modules = new Module[] { // collect the modules
        new CircularShift(input), // circular shift module
        new Alphabetizer(input), // alphabetizer module
        new NoiseRemover(input) // noise remover module
    };
    
    for(Module module : modules) // transform the lines
      module.transform();
    
    //Output output = new Output(input.getLines());

   //return output.getOutput(); // return the output
    return new Output(input);
  }

}
