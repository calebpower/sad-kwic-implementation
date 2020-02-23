package edu.uco.cs.group3_spring2020.kwic.domain.token;

/**
 * Denotes some word in a line.
 * 
 * @author Caleb L. Power
 */
public class Word {
  
  Character[] characters = null;
  
  /**
   * Overloaded constructor to create a word.
   * 
   * @param word the spelling of the word
   */
  public Word(String word) {
    characters = new Character[word.length()];
    for(int i = 0; i < word.length(); i++)
      characters[i] = new Character(word.charAt(i));
  }
  
  /**
   * Compares this word to another word.
   * 
   * @param w another word
   * @return a ComparisonResult in accordance to whether this word is lesser
   *         than, greater than, or equal to another word.
   * @see ComparisonResult
   */
  public ComparisonResult compareTo(Word w) {
    int limit = characters.length < w.characters.length
        ? characters.length : w.characters.length;
    
    for(int i = 0; i < limit; i++) {
      ComparisonResult result = characters[i].compareTo(w.characters[i]);
      switch(result) {
      case EQUAL_TO:
        continue;
      default:
        return result;
      }
    }
    
    return characters.length < w.characters.length
        ? ComparisonResult.LESS_THAN
            : (characters.length > w.characters.length
                ? ComparisonResult.GREATER_THAN : ComparisonResult.EQUAL_TO);
  }
  
  /**
   * {@inheritDoc}
   */
  @Override public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    for(int i = 0; i < characters.length; i++)
      stringBuilder.append(characters[i]);
    return stringBuilder.toString();
  }
  
  /**
   * Retrieves the characters in this word.
   * 
   * @return an array of Characters
   */
  public Character[] getCharacters() {
    return characters;
  }
  
}
