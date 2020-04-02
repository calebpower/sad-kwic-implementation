package edu.uco.cs.group3_spring2020.kwic.domain.pipes;

import org.json.JSONArray;
import org.json.JSONException;

import edu.uco.cs.group3_spring2020.kwic.action.hooks.PostContentHook;
import edu.uco.cs.group3_spring2020.kwic.domain.filter.AlphabetizeFilter;
import edu.uco.cs.group3_spring2020.kwic.domain.filter.CircularShiftFilter;
import edu.uco.cs.group3_spring2020.kwic.domain.filter.Filter;
import edu.uco.cs.group3_spring2020.kwic.domain.token.Line;

/**
 * Pipes user input through filters.
 * 
 * @author Caleb L. Power
 */
public class KWICPipe implements PostContentHook {
  
  /**
   * {@inheritDoc}
   */
  @Override public JSONArray pipe(JSONArray input) throws JSONException {
    long startTime = System.currentTimeMillis();
    
    Line[] lines = new Line[input.length()]; // create an empty array of lines
    
    for(int i = 0; i < lines.length; i++) // deserialize the JSON
      lines[i] = new Line(input.getString(i));
    
    final Filter[] filters = new Filter[] { // collect the filters
        new CircularShiftFilter(), // circular shift filter
        new AlphabetizeFilter() // alphabetizing filter
    };
    
    for(Filter filter : filters) // filter the lines
      lines = filter.filter(lines);
    
    JSONArray output = new JSONArray(); // create empty output
    for(Line line : lines) // serialize the output
      output.put(line.toString());
    
    long stopTime = System.currentTimeMillis();
    System.out.println("Operation completed in " + (stopTime - startTime) + " milliseconds.");
    
    return output; // return the output
  }

}
