package edu.uco.cs.group3_spring2020.kwic.domain.token;

import static org.junit.Assert.assertTrue;

import java.util.LinkedHashMap;
import java.util.Map.Entry;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests for the word model.
 * 
 * @author Caleb L. Power
 */
public class WordTest {
  
  private static Word w1 = null;
  private static Word w2 = null;
  private static Word w3 = null;
  private static Word w4 = null;
  private static Word w5 = null;
  private static Word w6 = null;
  private static Word w7 = null;
  
  /**
   * Instantiate words before the tests.
   */
  @BeforeClass public static void beforeTests() {
    w1 = new Word("test");
    w2 = new Word("Test");
    w3 = new Word("testing");
    w4 = new Word("test'ing");
    w5 = new Word("Te/t");
    w6 = new Word("test");
    w7 = new Word("Text");
  }
  
  /**
   * Make sure that word comparison works.
   */
  @Test public void testCompareTo() {
    new LinkedHashMap<Entry<String, ComparisonResult>, Entry<Word, Word>>() {
      private static final long serialVersionUID = -114525477689441361L; {
        put(new SimpleEntry<>("test(Test)", ComparisonResult.LESS_THAN), new SimpleEntry<>(w1, w2));
        put(new SimpleEntry<>("Test(test)", ComparisonResult.GREATER_THAN), new SimpleEntry<>(w2, w1));
        put(new SimpleEntry<>("Test(Te/t)", ComparisonResult.LESS_THAN), new SimpleEntry<>(w2, w5));
        put(new SimpleEntry<>("Te/t(Test)", ComparisonResult.GREATER_THAN), new SimpleEntry<>(w5, w2));
        put(new SimpleEntry<>("Text(Te/t)", ComparisonResult.GREATER_THAN), new SimpleEntry<>(w7, w5));
        put(new SimpleEntry<>("Te/t(Text)", ComparisonResult.LESS_THAN), new SimpleEntry<>(w5, w7));
        put(new SimpleEntry<>("testing(test'ing)", ComparisonResult.LESS_THAN), new SimpleEntry<>(w3, w4));
        put(new SimpleEntry<>("test'ing(testing)", ComparisonResult.GREATER_THAN), new SimpleEntry<>(w4, w3));
        put(new SimpleEntry<>("test(testing)", ComparisonResult.LESS_THAN), new SimpleEntry<>(w1, w3));
        put(new SimpleEntry<>("testing(test)", ComparisonResult.GREATER_THAN), new SimpleEntry<>(w3, w1));
        put(new SimpleEntry<>("test(test)", ComparisonResult.EQUAL_TO), new SimpleEntry<>(w1, w6));
    }}.forEach((k, v) -> {
      assertTrue(String.format("%1$s should be %2$s", k.getKey(), k.getValue().toString()),
          v.getKey().compareTo(v.getValue()) == k.getValue());
    });
  }
  
  /**
   * Make sure that word conversion to string works.
   */
  @Test public void testToString() {
    new LinkedHashMap<String, Word>() {
      private static final long serialVersionUID = -284033589685053024L; {
        put("Test", w2);
        put("test'ing", w4);
    }}.forEach((k, v) -> {
      assertTrue("Should be equal.", v.toString().equals(k));
    });
  }
  
  /**
   * Make sure that character retrieval from word works.
   */
  @Test public void testGetCharacters() {
    new LinkedHashMap<Word, Character[]>() {
      private static final long serialVersionUID = 8037868849679212478L; {
        put(w2, new Character[] {
            new Character('T'),
            new Character('e'),
            new Character('s'),
            new Character('t')
        });
        put(w4, new Character[] {
            new Character('t'),
            new Character('e'),
            new Character('s'),
            new Character('t'),
            new Character('\''),
            new Character('i'),
            new Character('n'),
            new Character('g')
        });
    }}.forEach((k, v) -> {
      Character[] characters = k.getCharacters();
      assertTrue("Expecting identical array sizes for " + k.toString(),
          characters.length == v.length);
      for(int i = 0; i < characters.length; i++)
        assertTrue("Expecting to find " + v[i].toString(), characters[i].toChar() == v[i].toChar());
    });
  }
}
