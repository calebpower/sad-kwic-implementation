package edu.uco.cs.group3_spring2020.kwic.domain.token;

/**
 * The result of some comparison between two KWIC tokens.
 * 
 * @author Caleb L. Power
 */
public enum ComparisonResult {
  
  /**
   * Indicates that the value of the acting token is greater than that of the
   * token argument.
   */
  GREATER_THAN,
  
  /**
   * Indicates that the value of the acting token is lesser than that of the
   * token argument.
   */
  LESS_THAN,
  
  /**
   * Indicates that the value of the acting token is equal to that of the token
   * argument.
   */
  EQUAL_TO,
  
  /**
   * Indicates that the originating object is invalid (apples and oranges, and
   * the apple spoiled the barrel).
   */
  INVALID_OBJECT,
  
  /**
   * Indicates that the comparison argument is invalid (apples and oranges, and
   * the orange is bad).
   */
  INVALID_ARGUMENT
  
}
