package edu.uco.cs.group3_spring2020.kwic.domain.modules;

import org.json.JSONArray;

import edu.uco.cs.group3_spring2020.kwic.domain.token.Line;

/**
 * Serializes output to JSON
 *
 * @author Everistus Akpabio
 */
public class Output extends JSONArray {
  
    public Output(Input input) {
        for(Line line : input.lines) // serialize the output
            put(line.toString());
    }

}
