package edu.uco.cs.group3_spring2020.kwic.domain.token;

/**
 * Denotes some character in a word.
 * 
 * @author Caleb L. Power
 */
public class Character {
  
  char character;
  
  /**
   * Overloaded constructor to create a character.
   * 
   * @param c the raw character
   */
  public Character(char c) {
    this.character = c;
  }
  
  /**
   * Compares this character to another character.
   * 
   * @param c another character
   * @return a ComparisonResult in accordance to whether this character is
   *         lesser than, greater than, or equal to another character.
   * @see ComparisonResult
   */
  public ComparisonResult compareTo(Character c) {
    if(this.character - c.character < 0)
      return ComparisonResult.LESS_THAN;
    
    if(this.character - c.character > 0)
      return ComparisonResult.GREATER_THAN;
    
    return ComparisonResult.EQUAL_TO;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override public String toString() {
    return "" + character;
  }
  
  /**
   * Retrieves the raw character.
   * 
   * @return the raw character
   */
  public char toChar() {
    return character;
  }
  
}
