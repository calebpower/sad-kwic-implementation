package edu.uco.cs.group3_spring2020.kwic.domain.modules;

import static org.junit.Assert.assertTrue;

import java.util.LinkedHashMap;

import org.junit.Test;

import edu.uco.cs.group3_spring2020.kwic.domain.token.Line;

/**
 * Tests for the alphabetizing filter.
 * 
 * @author Caleb L. Power
 */
public class AlphabetizeFilterTest {
  
  /**
   * Tests the alphabetizing mechanism.
   */
  @Test public void testFilter() {
    final Line[] testInput = new Line[] {
        new Line("this is the first test"),
        new Line("is the first test this"),
        new Line("the first test this is"),
        new Line("first test this is the"),
        new Line("test this is the first"),
        new Line("here's another test"),
        new Line("another test here's"),
        new Line("test here's another"),
        new Line("blah blah third test"),
        new Line("blah third test blah"),
        new Line("third test blah blah"),
        new Line("test blah blah third")
     };
    
    final Line[] expectedOutput = new Line[] {
        new Line("another test here's"),
        new Line("blah blah third test"),
        new Line("blah third test blah"),
        new Line("first test this is the"),
        new Line("here's another test"),
        new Line("is the first test this"),
        new Line("test blah blah third"),
        new Line("test here's another"),
        new Line("test this is the first"),
        new Line("the first test this is"),
        new Line("third test blah blah"),
        new Line("this is the first test")
    };
    
    Input input = new Input(null);
    input.lines = testInput;
    Module module = new Alphabetizer(input);
    module.transform();
    
    new LinkedHashMap<String, Line[]>() {
      private static final long serialVersionUID = -3203518313748115804L; {
        put("Test Input", testInput);
        put("Expected Output", expectedOutput);
        put("Test Output", input.lines);
    }}.forEach((k, v) -> {
      System.out.println("\n" + k + ":");
      for(int i = 0; i < v.length; i++)
        System.out.println(v[i].toString());
    });
    
    assertTrue("Alphabetized output should have an appropriate number of lines.",
        expectedOutput.length == input.lines.length);
    
    for(int i = 0; i < expectedOutput.length; i++) {
      assertTrue("Line " + i + " of the test output should match line " + i + " of the expected output.",
          expectedOutput[i].toString().equals(input.lines[i].toString()));
    }
  }
}
