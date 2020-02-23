package edu.uco.cs.group3_spring2020.kwic.domain.token;

import static org.junit.Assert.assertTrue;

import java.util.LinkedHashMap;
import java.util.Map.Entry;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test for the character model.
 * 
 * @author Caleb L. Power
 */
public class CharacterTest {
  
  private static Character c1 = null;
  private static Character c2 = null;
  private static Character c3 = null;
  private static Character c4 = null;
  private static Character c5 = null;
  
  /**
   * Instantiate characters before the tests.
   */
  @BeforeClass public static void beforeTests() {
    c1 = new Character('A');
    c2 = new Character('x');
    c3 = new Character('\'');
    c4 = new Character('X');
    c5 = new Character('x');
  }
  
  /**
   * Make sure that character comparisons are working.
   */
  @Test public void testCompareTo() {
    new LinkedHashMap<Entry<String, ComparisonResult>, Entry<Character, Character>>() {
      private static final long serialVersionUID = 1136253767158685526L; {
        put(new SimpleEntry<>("A(x)", ComparisonResult.LESS_THAN), new SimpleEntry<>(c1, c2));
        put(new SimpleEntry<>("x(A)", ComparisonResult.GREATER_THAN), new SimpleEntry<>(c2, c1));
        put(new SimpleEntry<>("x(X)", ComparisonResult.LESS_THAN), new SimpleEntry<>(c5, c4));
        put(new SimpleEntry<>("X(x)", ComparisonResult.GREATER_THAN), new SimpleEntry<>(c4, c5));
        put(new SimpleEntry<>("x(x)", ComparisonResult.EQUAL_TO), new SimpleEntry<>(c2, c5));
        put(new SimpleEntry<>("'(A)", ComparisonResult.INVALID_OBJECT), new SimpleEntry<>(c3, c1));
        put(new SimpleEntry<>("A(')", ComparisonResult.INVALID_ARGUMENT), new SimpleEntry<>(c1, c3));
    }}.forEach((k, v) -> {
      assertTrue(String.format("%1$s should be %2$s", k.getKey(), k.getValue().toString()),
          v.getKey().compareTo(v.getValue()) == k.getValue());
    });
  }
  
  /**
   * Make sure that character conversions to strings are working.
   */
  @Test public void testToString() {
    new LinkedHashMap<String, Character>() {
      private static final long serialVersionUID = -7915863158324161037L; {
        put("A", c1);
        put("x", c2);
        put("'", c3);
    }}.forEach((k, v) -> {
      assertTrue("Should be equal.", v.toString().equals(k));
    });
  }
  
  /**
   * Make sure that character conversions into chars are working.
   */
  @Test public void testToChar() {
    new LinkedHashMap<java.lang.Character, Character>() {
      private static final long serialVersionUID = 2734385958757850987L; {
        put('A', c1);
        put('x', c2);
        put('\'', c3);
    }}.forEach((k, v) -> {
      assertTrue("Should be equal.", v.toChar() == k);
    });
  }
  
}









