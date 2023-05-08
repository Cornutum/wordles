//////////////////////////////////////////////////////////////////////////////
// 
//                    Copyright 2023, Cornutum Project
//                             www.cornutum.org
//
//////////////////////////////////////////////////////////////////////////////

package org.cornutum.wordle;

import org.junit.Test;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.io.File;
import java.util.Arrays;
import java.util.Optional;

import static java.util.Collections.emptyList;

/**
 * Tests reading word lists.
 */
public class WordReaderTest extends BaseTest
  {
  @Test
  public void whenEmpty()
    {
    // Given...
    String words = "";
    
    // When...
    runWithStdIO(
      () -> {
      assertThat( "Words", Wordles.readWords( Optional.empty()), is( emptyList()));
      },
      words,
      null);
    }
  
  @Test
  public void whenMultipleLines()
    {
    // Given...
    String words = " Shiny words could \n  \nHover  until   EVERY \nwoman\n  lands\n";
    
    // When...
    runWithStdIO(
      () -> {
      assertThat(
        "Words",
        Wordles.readWords( Optional.empty()),
        is( Arrays.asList( "SHINY", "WORDS", "COULD", "HOVER", "UNTIL", "EVERY", "WOMAN", "LANDS")));
      },
      words,
      null);
    }
  
  @Test
  public void whenFile()
    {
    // Given...
    File words = getResourceFile( "words-valid.txt");
    
    // When...
    runWithStdIO(
      () -> {
      assertThat(
        "Words",
        Wordles.readWords( Optional.of( words)),
        is( Arrays.asList( "BELOW", "LOVED", "HOVEL", "VOWEL", "BOWEL", "DOWEL", "YODEL", "MODEL", "YOKEL")));
      },
      words,
      null);
    }
  }
