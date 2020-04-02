package edu.uco.cs.group3_spring2020.kwic.domain.modules;

import edu.uco.cs.group3_spring2020.kwic.domain.token.Line;
import org.json.JSONArray;
import org.json.JSONException;

/**
 * Read data lines from the input medium and stores them in core
 *
 * @author Everistus Akpabio
 */
public class Input implements Module {
    Line[] lines;
    public Input(JSONArray dataInput) {
        lines = new Line[dataInput.length()]; // create an empty array of lines

        for(int i = 0; i < lines.length; i++) // deserialize the JSON
            lines[i] = new Line(dataInput.getString(i));
    }

    public Line[] getLines(){
        return lines;
    }
    public void setLines(Line[] lines){
        this.lines = lines;
    }

    @Override
    public Line[] transform(Line[] lines) {
        return new Line[0];
    }
}
