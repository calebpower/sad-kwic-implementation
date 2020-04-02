package edu.uco.cs.group3_spring2020.kwic.domain.modules;

import edu.uco.cs.group3_spring2020.kwic.domain.token.Line;

/**
 * Receives some input, modified is, and returns some output.
 * 
 * @author Caleb L. Power
 */
public interface Module {
  
  /**
   * Filters input through some filtering mechanism.
   * 
   * @param lines a list of strings to denote the input
   * @return a list of strings to denote the output
   */
  public Line[] transform(Line[] lines);
  
}
