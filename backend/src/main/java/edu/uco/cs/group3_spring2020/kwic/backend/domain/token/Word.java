package edu.uco.cs.group3_spring2020.kwic.backend.domain.token;

import java.util.Arrays;

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
    for(int i = 0, j = 0; i < characters.length && j < w.characters.length; j += (i + 1 - i++)) {
      ComparisonResult result = characters[i].compareTo(w.characters[j]);
      
      if(result == ComparisonResult.INVALID_ARGUMENT) i--;
      else if(result == ComparisonResult.INVALID_OBJECT) j--;
      else if(result == ComparisonResult.LESS_THAN
          || result == ComparisonResult.GREATER_THAN)
        return result;
      
      continue;
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
  
  @Override public int hashCode() {
    return Arrays.hashCode(characters);
  }
  
  @Override public boolean equals(Object o) {
    return o.hashCode() == hashCode();
  }
  
}
