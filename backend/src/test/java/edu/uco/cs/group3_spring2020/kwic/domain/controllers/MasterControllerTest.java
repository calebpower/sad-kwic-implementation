package edu.uco.cs.group3_spring2020.kwic.domain.controllers;

import static org.junit.Assert.assertTrue;

import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import edu.uco.cs.group3_spring2020.kwic.api.Entry;
import edu.uco.cs.group3_spring2020.kwic.api.message.SetEntriesRequest;
import edu.uco.cs.group3_spring2020.kwic.backend.domain.controllers.LineManager;
import edu.uco.cs.group3_spring2020.kwic.backend.domain.token.Line;

/**
 * Tests for the KWIC* Pipe.
 * 
 * @author Caleb L. Power
 */
public class MasterControllerTest {
  
  /**
   * Tests the KWIC* piping mechanism.
   */
  @Test public void testPipe() {
    final JSONArray testInput = new JSONArray()
        .put("this is the first test")
        .put("here's another test")
        .put("blah blah third test");
    
    final JSONArray expectedOutput = new JSONArray()
        .put("another test here's")
        .put("blah blah third test")
        .put("blah third test blah")
        .put("first test this is the")
        .put("here's another test")
        // .put("is the first test this")
        .put("test blah blah third")
        .put("test here's another")
        .put("test this is the first")
        // .put("the first test this is")
        .put("third test blah blah")
        .put("this is the first test");
    
    final LineManager lineManager = new LineManager();
    SetEntriesRequest request = new SetEntriesRequest(new JSONObject().put("lines", testInput));
    lineManager.setEntries(request);
    Map<Line, Entry> output = lineManager.getEntries();
    JSONArray testOutput = new JSONArray();
    for(Line line : output.keySet())
      testOutput.put(line);
    
    new LinkedHashMap<String, JSONArray>() {
      private static final long serialVersionUID = -7225417351480410157L; {
        put("Test Input", testInput);
        put("Expected Output", expectedOutput);
        put("Test Output", testOutput);
    }}.forEach((k, v) -> {
      System.out.println("\n" + k + ":");
      System.out.println(v.toString(2));
    });
    
    assertTrue("Input and output should have the same number of elements",
        expectedOutput.length() == testOutput.length());
    
    assertTrue("Output should be an alphabatized and circularly-shifted representation of the input",
        expectedOutput.toString(2).equals(testOutput.toString(2)));
  }
  
}
