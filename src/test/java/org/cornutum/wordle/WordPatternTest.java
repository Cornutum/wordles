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

/**
 * Runs tests for {@link WordPattern}.
 */
public class WordPatternTest
  {
  @Test
  public void whenNone()
    {
    // Given...
    String target = "EVERY";
    String guess = "COULD";
    
    // When...
    WordPattern pattern = WordPattern.patternFor( target, guess);

    // Then...
    assertThat( "Pattern", String.valueOf( pattern), is( "....."));
    }
  
  @Test
  public void whenSome()
    {
    // Given...
    String target = "EVERY";
    String guess = "MEALY";
    
    // When...
    WordPattern pattern = WordPattern.patternFor( target, guess);

    // Then...
    assertThat( "Pattern", String.valueOf( pattern), is( ".y..G"));
    }
  
  @Test
  public void whenGuessMultipleSome()
    {
    // Given...
    String target = "MEALY";
    String guess = "EVERY";
    
    // When...
    WordPattern pattern = WordPattern.patternFor( target, guess);

    // Then...
    assertThat( "Pattern", String.valueOf( pattern), is( "y...G"));
    }
  
  @Test
  public void whenTargetMultipleMissed()
    {
    // Given...
    String target = "EVERY";
    String guess  = "LEVEL";
    
    // When...
    WordPattern pattern = WordPattern.patternFor( target, guess);

    // Then...
    assertThat( "Pattern", String.valueOf( pattern), is( ".yyy."));
    }
  
  @Test
  public void whenAll()
    {
    // Given...
    String target = "EVERY";
    String guess  = "EVERY";
    
    // When...
    WordPattern pattern = WordPattern.patternFor( target, guess);

    // Then...
    assertThat( "Pattern", String.valueOf( pattern), is( "GGGGG"));
    }
  
  @Test
  public void whenAnagram()
    {
    // Given...
    String target = "TASER";
    String guess  = "STARE";
    
    // When...
    WordPattern pattern = WordPattern.patternFor( target, guess);

    // Then...
    assertThat( "Pattern", String.valueOf( pattern), is( "yyyyy"));
    }
  
  @Test
  public void whenGuessMultipleMixed()
    {
    // Given...
    String target = "BUMPY";
    String guess  = "GUPPY";
    
    // When...
    WordPattern pattern = WordPattern.patternFor( target, guess);

    // Then...
    assertThat( "Pattern", String.valueOf( pattern), is( ".G.GG"));
    }
  }
