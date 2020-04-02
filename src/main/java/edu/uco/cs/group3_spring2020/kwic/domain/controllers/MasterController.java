package edu.uco.cs.group3_spring2020.kwic.domain.controllers;

import edu.uco.cs.group3_spring2020.kwic.action.hooks.PostContentHook;
import edu.uco.cs.group3_spring2020.kwic.domain.modules.*;
import edu.uco.cs.group3_spring2020.kwic.domain.modules.Module;
import edu.uco.cs.group3_spring2020.kwic.domain.token.Line;
import org.json.JSONArray;
import org.json.JSONException;

/**
 * Handles sequencing of other modules
 * 
 * @author Caleb L. Power
 */
public class MasterController implements PostContentHook {
  
  /**
   * {@inheritDoc}
   */
  @Override public JSONArray pipe(JSONArray dataInput) throws JSONException {
    Input input = new Input(dataInput);
    
    final Module[] modules = new Module[] { // collect the filters
        new CircularShift(), // circular shift module
        new Alphabetizer(), // alphabetizer module
        new NoiseRemover() // noise remover module
    };
    
    for(Module module : modules) // filter the lines
      input.setLines(module.transform(input.getLines()));
    
    Output output = new Output(input.getLines());

   return output.getOutput(); // return the output
  }

}
