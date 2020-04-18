package edu.uco.cs.group3_spring2020.kwic.api;

import org.json.JSONObject;

/**
 * Denotes an entry.
 * 
 * @author Caleb L. Power
 */
public class Entry {
  
  private String url = null;
  private String description = null;
  
  /**
   * Constructs an entry from a URL and a description.
   * 
   * @param url the url in question
   * @param description the description in question
   */
  public Entry(String url, String description) {
    this.url = url;
    this.description = description;
  }
  
  /**
   * Deserializes an entry from a JSON object
   * 
   * @param json the JSON object
   */
  public Entry(JSONObject json) {
    this.url = json.getString("url");
    this.description = json.getString("description");
  }
  
  /**
   * Retrieves the URL
   * 
   * @return the URL in question
   */
  public String getURL() {
    return url;
  }
  
  /**
   * Retrieves the description.
   * 
   * @return the description
   */
  public String getDescription() {
    return description;
  }
  
  /**
   * Serializes the entry.
   * 
   * @return a JSON object containing serialized entry data
   */
  public JSONObject serialize() {
    return new JSONObject()
        .put("url", url)
        .put("description", description);
  }
}
