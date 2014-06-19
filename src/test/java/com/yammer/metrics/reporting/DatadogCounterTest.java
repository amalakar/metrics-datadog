package com.yammer.metrics.reporting;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import com.yammer.metrics.reporting.model.DatadogCounter;

public class DatadogCounterTest {

  @Test
  public void testSplitNameAndTags() {
    List<String> tags = new ArrayList<String>();
    tags.add("env:prod");
    tags.add("version:1.0.0");
    DatadogCounter counter = new DatadogCounter(
        "test[tag1:value1,tag2:value2,tag3:value3]", 1L, 1234L, "Test Host", tags);
    List<String> allTags = counter.getTags();

    assertEquals(5, allTags.size());
    assertEquals("tag1:value1", allTags.get(0));
    assertEquals("tag2:value2", allTags.get(1));
    assertEquals("tag3:value3", allTags.get(2));
    assertEquals("env:prod", allTags.get(3));
    assertEquals("version:1.0.0", allTags.get(4));
  }

  @Ignore("Rely on datadog to strip tags")
  @Test
  public void testStripInvalidCharsFromTags() {
    DatadogCounter counter = new DatadogCounter(
        "test[tag1:va  lue1,tag2:va .%lue2,ta  %# g3:value3]", 1L, 1234L, "Test Host", null);
    List<String> tags = counter.getTags();

    assertEquals(3, tags.size());
    assertEquals("tag1:value1", tags.get(0));
    assertEquals("tag2:value2", tags.get(1));
    assertEquals("tag3:value3", tags.get(2));
  }

}
