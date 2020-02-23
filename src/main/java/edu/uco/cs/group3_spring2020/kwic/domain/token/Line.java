package edu.uco.cs.group3_spring2020.kwic.domain.token;

/**
 * Denotes some line with words.
 * 
 * @author Caleb L. Power
 */
public class Line {
  
  Word[] words = null;
  
  /**
   * Overloaded constructor to create a line.
   * 
   * @param line the spelling of the words in the line
   */
  public Line(String line) {
    String[] words = line.split("\\s+");
    this.words = new Word[words.length];
    for(int i = 0; i < words.length; i++)
      this.words[i] = new Word(words[i]);
  }
  
  /**
   * Overloaded to create a line.
   * 
   * @param words the individual words in the line
   */
  public Line(Word[] words) {
    this.words = words;
  }
  
  /**
   * Compares this line to another line.
   * 
   * @param l another line
   * @return a ComparisonResult in accordance to whether this line is lesser
   *         than, greater than, or equal to another word.
   * @see ComparisonResult
   */
  public ComparisonResult compareTo(Line l) {
    int limit = words.length < l.words.length
        ? words.length : l.words.length;
    
    for(int i = 0; i < limit; i++) {
      ComparisonResult result = words[i].compareTo(l.words[i]);
      switch(result) {
      case EQUAL_TO:
        continue;
      default:
        return result;
      }
    }
    
    return words.length < l.words.length
        ? ComparisonResult.LESS_THAN
            : (words.length > l.words.length
                ? ComparisonResult.GREATER_THAN : ComparisonResult.EQUAL_TO);
  }
  
  /**
   * {@inheritDoc}
   */
  @Override public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    for(int i = 0; i < words.length; i++) {
      if(i > 0) stringBuilder.append(' ');
      stringBuilder.append(words[i].toString());
    }
    return stringBuilder.toString();
  }
  
  /**
   * Retrieves the words in this line.
   * 
   * @return an array of Words
   */
  public Word[] getWords() {
    return words;
  }
}
