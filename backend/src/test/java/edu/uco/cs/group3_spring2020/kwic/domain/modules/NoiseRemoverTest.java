package edu.uco.cs.group3_spring2020.kwic.domain.modules;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.junit.Test;

import edu.uco.cs.group3_spring2020.kwic.api.Entry;
import edu.uco.cs.group3_spring2020.kwic.backend.domain.modules.Module;
import edu.uco.cs.group3_spring2020.kwic.backend.domain.modules.NoiseRemover;
import edu.uco.cs.group3_spring2020.kwic.backend.domain.token.Line;

/**
 * Tests for the noise removing module.
 * 
 * @author Caleb L. Power
 */
public class NoiseRemoverTest {
  
  /**
   * Tests the noise removing mechanism.
   */
  @Test public void testTransform() {
    final List<Entry> entries = new ArrayList<>();
    entries.add(new Entry("http://www.google.com", "this is the first test"));
    entries.add(new Entry("http://www.axonibyte.com", "here's another test"));
    entries.add(new Entry("http://www.meepcraft.com", "blah blah third test"));
    entries.add(new Entry("http://www.uco.edu", "abcde 12345 67wx yz89"));
    
    final LinkedHashMap<Line, Entry> testInput = new LinkedHashMap<>();
    testInput.put(new Line("this is the first test"), entries.get(0));
    testInput.put(new Line("is the first test this"), entries.get(0));
    testInput.put(new Line("the first test this is"), entries.get(0));
    testInput.put(new Line("first test this is the"), entries.get(0));
    testInput.put(new Line("test this is the first"), entries.get(0));
    testInput.put(new Line("here's another test"), entries.get(1));
    testInput.put(new Line("another test here's"), entries.get(1));
    testInput.put(new Line("test here's another"), entries.get(1));
    testInput.put(new Line("blah blah third test"), entries.get(2));
    testInput.put(new Line("blah third test blah"), entries.get(2));
    testInput.put(new Line("third test blah blah"), entries.get(2));
    testInput.put(new Line("test blah blah third"), entries.get(2));
    testInput.put(new Line("abcde 12345 67wx yz89"), entries.get(3));
    testInput.put(new Line("12345 67wx yz89 abcde"), entries.get(3));
    testInput.put(new Line("67wx yz89 abcde 12345"), entries.get(3));
    testInput.put(new Line("yz89 abcde 12345 67wx"), entries.get(3));
    
    final LinkedHashMap<Line, Entry> expectedOutput = new LinkedHashMap<>();
    expectedOutput.put(new Line("this is the first test"), entries.get(1));
    expectedOutput.put(new Line("first test this is the"), entries.get(1));
    expectedOutput.put(new Line("test this is the first"), entries.get(1));
    expectedOutput.put(new Line("here's another test"), entries.get(2));
    expectedOutput.put(new Line("another test here's"), entries.get(2));
    expectedOutput.put(new Line("test here's another"), entries.get(2));
    expectedOutput.put(new Line("blah blah third test"), entries.get(3));
    expectedOutput.put(new Line("blah third test blah"), entries.get(3));
    expectedOutput.put(new Line("third test blah blah"), entries.get(3));
    expectedOutput.put(new Line("test blah blah third"), entries.get(3));
    expectedOutput.put(new Line("abcde 12345 67wx yz89"), entries.get(4));
    expectedOutput.put(new Line("12345 67wx yz89 abcde"), entries.get(4));
    expectedOutput.put(new Line("67wx yz89 abcde 12345"), entries.get(4));
    expectedOutput.put(new Line("yz89 abcde 12345 67wx"), entries.get(4));
    
    Module module = new NoiseRemover(testInput);
    module.transform();
    
    assertTrue("Noiseless output should have an appropriate number of lines",
        expectedOutput.size() == testInput.size());
    
    
    Object[][] eoLines = new Line[expectedOutput.size()][2];
    Object[][] tiLines = new Line[testInput.size()][2];
    
    int c = 0;
    for(Line line : expectedOutput.keySet()) {
      eoLines[c][0] = line;
      eoLines[c++][1] = expectedOutput.get(line);
    }
    
    c = 0;
    for(Line line : testInput.keySet()) {
      tiLines[c][0] = line;
      tiLines[c++][1] = testInput.get(line);
    }
    
    for(int i = 0; i < eoLines.length; i++) {
      assertTrue("Line " + i + " of the test output should match line " + i + " of the expected output.",
          eoLines[i][0].toString().equals(tiLines[i][0].toString()));
      assertTrue("Line " + i + " of the test output should have the same entry as line " + i + " of the expected output.",
          eoLines[i][1].equals(tiLines[i][1]));
    }
  }
  
}
