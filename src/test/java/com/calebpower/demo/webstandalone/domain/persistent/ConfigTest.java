package com.calebpower.demo.webstandalone.domain.persistent;

import org.junit.Test;

import com.calebpower.demo.webstandalone.domain.persistent.Config;

import static org.junit.Assert.*;

/**
 * Example test case for Config; ensures that the default port is indeed 4567.
 * 
 * @author Caleb L. Power
 */
public class ConfigTest {
  @Test public void testSparkPort() {
    Config config = new Config("."); //the directory is inconsequential
    assertTrue("Default Spark port should be 4567.", config.getSparkPort() == 4567);
  }
}
