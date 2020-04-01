package edu.uco.cs.group3_spring2020.kwic.domain.controllers;

import edu.uco.cs.group3_spring2020.kwic.action.hooks.PostContentHook;
import edu.uco.cs.group3_spring2020.kwic.domain.modules.Alphabetizer;
import edu.uco.cs.group3_spring2020.kwic.domain.modules.CircularShift;
import edu.uco.cs.group3_spring2020.kwic.domain.modules.Filter;
import edu.uco.cs.group3_spring2020.kwic.domain.modules.NoiseRemover;
import edu.uco.cs.group3_spring2020.kwic.domain.token.Line;
import org.json.JSONArray;
import org.json.JSONException;

/**
 * Pipes user input through filters.
 * 
 * @author Caleb L. Power
 */
public class MasterController implements PostContentHook {
  
  /**
   * {@inheritDoc}
   */
  @Override public JSONArray pipe(JSONArray input) throws JSONException {
    Line[] lines = new Line[input.length()]; // create an empty array of lines
    
    for(int i = 0; i < lines.length; i++) // deserialize the JSON
      lines[i] = new Line(input.getString(i));
    
    final Filter[] filters = new Filter[] { // collect the filters
        new CircularShift(), // circular shift module
        new Alphabetizer(), // alphabetizer module
        new NoiseRemover() // noise remover module
    };
    
    for(Filter filter : filters) // filter the lines
      lines = filter.filter(lines);
    
    JSONArray output = new JSONArray(); // create empty output
    for(Line line : lines) // serialize the output
      output.put(line.toString());
    
    return output; // return the output
  }

}