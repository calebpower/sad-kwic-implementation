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
    char mineLowered = java.lang.Character.toLowerCase(this.character);
    char theirsLowered = java.lang.Character.toLowerCase(c.character);
    
    // reject non-alpha characters
    if(mineLowered < 'a' || mineLowered > 'z') return ComparisonResult.INVALID_OBJECT;
    if(theirsLowered < 'a' || theirsLowered > 'z') return ComparisonResult.INVALID_ARGUMENT;
    
    // potentially same letters, possibly different cases
    if(mineLowered == theirsLowered) { // convert to lowercase values, depend on ASCII values
      if(this.character - c.character < 0) return ComparisonResult.GREATER_THAN;
      if(this.character - c.character > 0) return ComparisonResult.LESS_THAN;
      return ComparisonResult.EQUAL_TO; // exit early, avoid other comparisons
    }
    
    // different letters
    if(mineLowered - theirsLowered < 0) return ComparisonResult.LESS_THAN;
    if(mineLowered - theirsLowered > 0) return ComparisonResult.GREATER_THAN;
    
    // whatever, they're the same
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
