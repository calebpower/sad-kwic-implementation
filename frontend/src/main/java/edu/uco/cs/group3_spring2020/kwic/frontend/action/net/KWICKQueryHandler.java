package edu.uco.cs.group3_spring2020.kwic.frontend.action.net;

import java.util.Set;
import java.util.UUID;

import com.axonibyte.bonemesh.BoneMesh;

import edu.uco.cs.group3_spring2020.kwic.api.Entry;
import edu.uco.cs.group3_spring2020.kwic.frontend.action.hooks.SearchContentHook;

public class KWICKQueryHandler implements SearchContentHook {
  
  private BoneMesh boneMesh = null;
  
  public void linkBoneMesh(BoneMesh boneMesh) {
    this.boneMesh = boneMesh;
  }

  @Override public UUID dispatch(Set<String> keywords) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override public Set<Entry> getResults(UUID uuid, int timeout) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override public boolean onResponse(UUID uuid, Set<Entry> entries) {
    // TODO Auto-generated method stub
    return false;
  }

}
