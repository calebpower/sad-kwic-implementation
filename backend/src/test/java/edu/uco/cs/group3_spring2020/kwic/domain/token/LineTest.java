package edu.uco.cs.group3_spring2020.kwic.domain.token;

import static org.junit.Assert.assertTrue;

import java.util.LinkedHashMap;
import java.util.Map.Entry;

import org.junit.BeforeClass;
import org.junit.Test;

import edu.uco.cs.group3_spring2020.kwic.backend.domain.token.ComparisonResult;
import edu.uco.cs.group3_spring2020.kwic.backend.domain.token.Line;
import edu.uco.cs.group3_spring2020.kwic.backend.domain.token.Word;

/**
 * Tests for the line model.
 * 
 * @author Caleb L. Power
 */
public class LineTest {
  
  private static Line l1 = null;
  private static Line l2 = null;
  private static Line l3 = null;
  private static Line l4 = null;
  private static Line l5 = null;
  
  /**
   * Instantiate lines before the tests.
   */
  @BeforeClass public static void beforeTests() {
    l1 = new Line("this is a test line");
    l2 = new Line("bowties are cool");
    l3 = new Line("bowties are cool and so are fezzes");
    l4 = new Line("bowties are cool");
    l5 = new Line("this is a test");
  }
  
  /**
   * Make sure that line comparison works.
   */
  @Test public void testCompareTo() {
    new LinkedHashMap<Entry<String, ComparisonResult>, Entry<Line, Line>>() {
      private static final long serialVersionUID = 1L; {
        put(new SimpleEntry<>("this is a test line(bowties are cool)", ComparisonResult.GREATER_THAN),
            new SimpleEntry<>(l1, l2));
        put(new SimpleEntry<>("bowties are cool(this is a test line)", ComparisonResult.LESS_THAN),
            new SimpleEntry<>(l2, l1));
        put(new SimpleEntry<>("bowties are cool(bowties are cool)", ComparisonResult.EQUAL_TO),
            new SimpleEntry<>(l2, l4));
        put(new SimpleEntry<>("bowties are cool(bowties and fezzes are cool)", ComparisonResult.LESS_THAN),
            new SimpleEntry<>(l2, l3));
        put(new SimpleEntry<>("bowties and fezzes are cool(bowties are cool)", ComparisonResult.GREATER_THAN),
            new SimpleEntry<>(l3, l2));
        put(new SimpleEntry<>("this is a test(this is a test line)", ComparisonResult.LESS_THAN),
            new SimpleEntry<>(l5, l1));
        put(new SimpleEntry<>("this is a test line(this is a test)", ComparisonResult.GREATER_THAN),
            new SimpleEntry<>(l1, l5));
    }}.forEach((k, v) -> {
      assertTrue(String.format("%1$s should be %2$s", k.getKey(), k.getValue().toString()),
          v.getKey().compareTo(v.getValue()) == k.getValue());
    });
  }
  
  /**
   * Make sure that line conversion to string works.
   */
  @Test public void testToString() {
    new LinkedHashMap<String, Line>() {
      private static final long serialVersionUID = 6957419014417538095L; {
        put("bowties are cool", l2);
        put("this is a test", l5);
    }}.forEach((k, v) -> {
      assertTrue("Should be equal.", v.toString().equals(k));
    });
  }
  
  /**
   * Make sure that word retrieval from line works.
   */
  @Test public void testGetWords() {
    new LinkedHashMap<Line, Word[]>() {
      private static final long serialVersionUID = 8958207285985451071L; {
        put(l2, new Word[] {
           new Word("bowties"),
           new Word("are"),
           new Word("cool")
        });
        put(l5, new Word[] {
           new Word("this"),
           new Word("is"),
           new Word("a"),
           new Word("test")
        });
    }}.forEach((k, v) -> {
      Word[] words = k.getWords();
      assertTrue("Expecting identical array sizes for " + k.toString(),
          words.length == v.length);
      for(int i = 0; i < words.length; i++)
        assertTrue("Expecting to find " + v[i].toString(), words[i].toString().equals(v[i].toString()));
    });
  }
  
}
