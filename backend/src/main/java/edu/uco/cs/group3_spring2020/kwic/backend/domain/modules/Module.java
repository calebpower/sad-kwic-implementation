package edu.uco.cs.group3_spring2020.kwic.backend.domain.modules;

import java.util.Map;

import edu.uco.cs.group3_spring2020.kwic.api.Entry;
import edu.uco.cs.group3_spring2020.kwic.backend.domain.token.Line;

/**
 * Receives some input, modified is, and returns some output.
 * 
 * @author Caleb L. Power
 */
public abstract class Module {
  
  protected Map<Line, Entry> entries = null;
  
  /**
   * Overloaded constructor to set the input
   * 
   * @param entries the input
   */
  public Module(Map<Line, Entry> entries) {
    this.entries = entries;
  }
  
  /**
   * Transforms input.
   */
  public abstract void transform();
  
}
