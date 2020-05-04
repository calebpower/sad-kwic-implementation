package edu.uco.cs.group3_spring2020.kwic.domain.state;

import org.json.JSONArray;

import edu.uco.cs.group3_spring2020.kwic.domain.token.Line;

/**
 * Read data lines from the input medium and stores them in core
 *
 * @author Everistus Akpabio, Caleb L. Power
 */
public class Input  {
    Line[] lines = null;
    
    /**
     * Overloaded constructor to deserialize JSON
     * 
     * @param dataInput the input JSON array
     */
    public Input(JSONArray dataInput) {
      if(dataInput == null) return;
      
      lines = new Line[dataInput.length()]; // create an empty array of lines

      for(int i = 0; i < lines.length; i++) // deserialize the JSON
        lines[i] = new Line(dataInput.getString(i));
    }

}
