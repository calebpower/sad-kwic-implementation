package edu.uco.cs.group3_spring2020.kwic.backend.domain.controllers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONException;

import edu.uco.cs.group3_spring2020.kwic.api.Entry;
import edu.uco.cs.group3_spring2020.kwic.api.message.SearchRequest;
import edu.uco.cs.group3_spring2020.kwic.api.message.SetEntriesRequest;
import edu.uco.cs.group3_spring2020.kwic.backend.KWICBackend;
import edu.uco.cs.group3_spring2020.kwic.backend.action.hooks.InitiateSearchHook;
import edu.uco.cs.group3_spring2020.kwic.backend.action.hooks.SearchResponseHook;
import edu.uco.cs.group3_spring2020.kwic.backend.action.hooks.SetLinesHook;
import edu.uco.cs.group3_spring2020.kwic.backend.domain.modules.Alphabetizer;
import edu.uco.cs.group3_spring2020.kwic.backend.domain.modules.CircularShifter;
import edu.uco.cs.group3_spring2020.kwic.backend.domain.modules.Module;
import edu.uco.cs.group3_spring2020.kwic.backend.domain.modules.NoiseRemover;
import edu.uco.cs.group3_spring2020.kwic.backend.domain.token.Line;
import edu.uco.cs.group3_spring2020.kwic.backend.domain.token.Word;

/**
 * Handles sequencing of other modules
 * 
 * @author Caleb L. Power
 */
public class LineManager implements InitiateSearchHook, SetLinesHook {
  
  private Map<Line, Entry> entries = null;
  private Map<Word, Set<Entry>> keywords = null;
  private SearchResponseHook searchResponseHook = null;
  
  /**
   * Instantiates the manager.
   */
  public LineManager() {
    this.entries = new LinkedHashMap<>();
    this.keywords = new HashMap<>();
  }
  
  /**
   * Sets the search response hook.
   * 
   * @param hook the hook
   * @return this LineManager object
   */
  public LineManager setSearchResponseHook(SearchResponseHook hook) {
    this.searchResponseHook = hook;
    return this;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override public void setEntries(SetEntriesRequest request) throws JSONException {
    final long startTime = System.currentTimeMillis();
    
    Set<Entry> entries = request.getEntries();
    
    synchronized(entries) {    
      this.entries.clear();
      for(Entry e : entries) {
        KWICBackend.getLogger().onDebug("LINE_MANAGER", "Received entry: " + e.serialize());
        this.entries.put(new Line(e.getDescription()), e);
      }
      
      final Module[] modules = new Module[] { // collect the modules
          new CircularShifter(this.entries), // circular shift module
          new Alphabetizer(this.entries), // alphabetizer module
          new NoiseRemover(this.entries) // noise remover module
      };
      
      for(Module module : modules) // transform the lines
        module.transform();
      
      keywords.clear();
      for(Line line : this.entries.keySet()) {
        Entry entry = this.entries.get(line);
        KWICBackend.getLogger().onDebug("LINE_MANAGER",
            String.format("Saving entry url = %1$s, modified description = %2$s",
            entry.getURL(),
            line.toString()));
        Word word = line.getWords()[0];
        if(!keywords.containsKey(word)) keywords.put(word, new HashSet<>());
        if(!keywords.get(word).contains(entry)) keywords.get(word).add(entry);
      }
    }
    
    final long stopTime = System.currentTimeMillis();
    KWICBackend.getLogger().onInfo("LINE_MANAGER", "SET operation completed in " + (stopTime - startTime) + " milliseconds.");
  }
  
  /**
   * Retrieves the post-transformation entries
   * 
   * @return the entries
   */
  public Map<Line, Entry> getEntries() {
    return entries;
  }
  
  @Override public void search(SearchRequest request) throws JSONException {
    final long startTime = System.currentTimeMillis();
    
    List<String> keys = request.getKeys();
    Map<Entry, Set<String>> keywordsByEntry = new HashMap<>();
    Set<Entry> results = new HashSet<>();
    
    synchronized(entries) {
      for(String key : keys) {
        Set<Entry> entries = keywords.get(new Word(key));
        if(entries == null) continue;
        for(Entry entry : entries) {
          if(!keywordsByEntry.containsKey(entry))
            keywordsByEntry.put(entry, new HashSet<>());
          if(!keywordsByEntry.get(entry).contains(key))
            keywordsByEntry.get(entry).add(key);
        }
      }
      
      for(Entry entry : keywordsByEntry.keySet())
        if(keywordsByEntry.get(entry).size() == keys.size())
          results.add(entry);
    }
    
    long stopTime = System.currentTimeMillis();
    KWICBackend.getLogger().onInfo("LINE_MANAGER", "SEARCH operation completed in " + (stopTime - startTime) + " milliseconds.");
    
    if(searchResponseHook != null)
      searchResponseHook.deploySearchResponse(request.getID(), results);
    else KWICBackend.getLogger().onError("LINE_MANAGER", "Hook not instantiated!");
  }

}
