package edu.uco.cs.group3_spring2020.kwic.backend.domain.persistent;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;

import edu.uco.cs.group3_spring2020.kwic.api.Entry;
import edu.uco.cs.group3_spring2020.kwic.backend.KWICBackend;

/**
 * Flat file database to save entries.
 * 
 * @author Caleb L. Power
 */
public class FlatfileDB {
  
  private File file = null;
  
  /**
   * Instantiates a flat file database.
   * 
   * @throws IOException to be thrown if a new file couldn't be created
   */
  public FlatfileDB() throws IOException {
    file = new File(".", "kwic.db");
    if(!file.exists()) file.createNewFile();
  }
  
  /**
   * Saves a set of entries to the disk.
   * 
   * @param entries the entries
   * @return <code>true</code> iff the entries were successfully saved
   */
  public boolean save(Set<Entry> entries) {
    try(PrintWriter out = new PrintWriter(file)) {
      for(Entry entry : entries)
        out.println(entry.serialize());
    } catch(FileNotFoundException e) {
      e.printStackTrace();
    }
    
    return false;
  }
  
  /**
   * Loads a set of entries from the disk.
   * 
   * @return a set of entries
   */
  public Set<Entry> load() {
    Set<Entry> entries = new HashSet<>();
    
    try(Scanner scanner = new Scanner(file)) {
      int c = 1;
      while(scanner.hasNext()) try {
        entries.add(new Entry(new JSONObject(scanner.nextLine())));
        c++;
      } catch(JSONException e) {
        KWICBackend.getLogger().onError("DATABASE", "Encountered a bad entry on line " + c);
      }
    } catch(FileNotFoundException e) {
      e.printStackTrace();
    }
    
    return entries;
  }
}
