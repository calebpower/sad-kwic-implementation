package edu.uco.cs.group3_spring2020.kwic.backend.domain.modules;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import edu.uco.cs.group3_spring2020.kwic.api.Entry;
import edu.uco.cs.group3_spring2020.kwic.backend.domain.token.Line;

/**
 * Returns a set of lines that don't begin with a noise word.
 * 
 * @author Caleb L. Power
 */
public class NoiseRemover extends Module {
  
  /**
   * Overloaded constructor.
   * 
   * @param entries the input
   */
  public NoiseRemover(Map<Line, Entry> entries) {
    super(entries);
  }
  
  private static Set<String> noiseWords = new HashSet<>() {
    private static final long serialVersionUID = -5073505350485807715L; {
      add("a");
      add("an");
      add("and");
      add("as");
      add("at");
      add("be");
      add("by");
      add("in");
      add("is");
      add("of");
      add("off");
      add("or");
      add("out");
      add("the");
      add("to");
  }};
  
  /**
   * Takes individual lines as input and returns a set of lines that don't
   * begin with a noise word.
   */
  @Override public void transform() {
    Map<Line, Entry> result = new LinkedHashMap<>();
    for(Line line : entries.keySet())
      if(!noiseWords.contains(line.getWords()[0].toString()))
        result.put(line, entries.get(line));
    entries.clear();
    for(Line line : result.keySet())
      entries.put(line, result.get(line));
  }
  
}
