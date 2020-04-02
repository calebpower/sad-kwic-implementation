package edu.uco.cs.group3_spring2020.kwic.domain.modules;

import edu.uco.cs.group3_spring2020.kwic.domain.token.Line;
import org.json.JSONArray;
import org.json.JSONException;

/**
 * Serializes output to JSON
 *
 * @author Everistus Akpabio
 */
public class Output implements Module {
    JSONArray output = new JSONArray(); // create empty output

    public Output(Line[] lines) {
        for(Line line : lines) // serialize the output
            output.put(line.toString());
    }

    public JSONArray getOutput() {
        return output;
    }

    @Override
    public Line[] transform(Line[] lines) {
        return new Line[0];
    }
}
