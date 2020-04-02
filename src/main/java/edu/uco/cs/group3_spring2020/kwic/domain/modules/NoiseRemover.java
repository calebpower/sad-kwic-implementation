package edu.uco.cs.group3_spring2020.kwic.domain.modules;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import edu.uco.cs.group3_spring2020.kwic.domain.token.Line;

/**
 * Returns a set of lines that don't begin with a noise word.
 * 
 * @author Caleb L. Power
 */
public class NoiseRemover extends Module {
  
  /**
   * Overloaded constructor.
   * 
   * @param input the input
   */
  public NoiseRemover(Input input) {
    super(input);
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
    List<Line> result = new LinkedList<>();
    for(Line line : input.lines)
      if(!noiseWords.contains(line.getWords()[0].toString()))
        result.add(0, line);
    Line[] resultArray = new Line[result.size()];
    for(int i = result.size() - 1; i >= 0; i--)
      resultArray[i] = result.remove(0);
    input.lines = resultArray;
  }
  
}
