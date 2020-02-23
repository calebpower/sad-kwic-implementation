package edu.uco.cs.group3_spring2020.kwic.domain.filter;

import static org.junit.Assert.assertTrue;

import java.util.LinkedHashMap;

import org.junit.Test;

import edu.uco.cs.group3_spring2020.kwic.domain.token.Line;

/**
 * Example test case for Config; ensures that the default port is indeed 4567.
 * 
 * @author Caleb L. Power
 */
public class CircularShiftFilterTest {
  
  /**
   * Tests the circular shift mechanism.
   */
  @Test public void testFilter() {    
    final Line[] testInput = new Line[] {
        new Line("this is the first test"),
        new Line("here's another test"),
        new Line("blah blah third test")
    };
    
    final Line[] expectedOutput = new Line[] {
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
    
    Filter filter = new CircularShiftFilter();
    Line[] testOutput = filter.filter(testInput);
    
    new LinkedHashMap<String, Line[]>() {
      private static final long serialVersionUID = 8260832695616525319L; {
        put("Test Input", testInput);
        put("Expected Output", expectedOutput);
        put("Test Output", testOutput);
    }}.forEach((k, v) -> {
      System.out.println("\n" + k + ":");
      for(int i = 0; i < v.length; i++)
        System.out.println(v[i].toString());
    });
    
    assertTrue("Circularly-shifted output should have an appropriate number of lines",
        expectedOutput.length == testOutput.length);
    
    for(int i = 0; i < expectedOutput.length; i++) {
      assertTrue("Line " + i + " of the test output should match line " + i + " of the expected output",
          expectedOutput[i].toString().equals(testOutput[i].toString()));
    }
  }
}
