package edu.uco.cs.group3_spring2020.kwic.domain.state;

import org.json.JSONArray;

import edu.uco.cs.group3_spring2020.kwic.domain.token.Line;

/**
 * Serializes output to JSON
 *
 * @author Everistus Akpabio, Caleb L. Power
 */
public class Output extends JSONArray {
  
  /**
   * Overloaded constructor.
   * 
   * @param input the input
   */
  public Output(Input input) {
    for(Line line : input.lines) // serialize the output
      put(line.toString());
  }

}
