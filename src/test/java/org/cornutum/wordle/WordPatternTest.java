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

import java.util.Arrays;
import java.util.List;
import static java.util.Collections.sort;
import static java.util.stream.Collectors.toList;

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
  
  @Test
  public void whenSorted()
    {
    // Given...
    String[] clues = {
      "yyy.G",
      "yyyG.",
      "yy.yG",
      "yy..G",
      "yy.Gy",
      "yy.G.",
      "yy.GG",
      "yyGy.",
      "yyG.y",
      "yyG..",
      "yyG.G",
      "yyGG.",
      "y.yyG",
      "y.y.G",
      "y.yGy",
      "y.yG.",
      "y.yGG",
      "y..yG",
      "y...G",
      "y..Gy",
      "y..G.",
      "y..GG",
      "y.Gyy",
      "y.Gy.",
      "y.GyG",
      "y.G.y",
      "y.G..",
      "y.G.G",
      "y.GGy",
      "y.GG.",
      "y.GGG",
      "yGyy.",
      "yGy.y",
      "yGy..",
      "yGy.G",
      "yGyG.",
      "yG.yy",
      "yG.y.",
      "yG.yG",
      "yG..y",
      "yG...",
      "yG..G",
      "yG.Gy",
      "yG.G.",
      "yG.GG",
      "yGGy.",
      "yGG.y",
      "yGG..",
      "yGG.G",
      "yGGG.",
      ".yyyG",
      ".yy.G",
      ".yyGy",
      ".yyG.",
      ".yyGG",
      ".y.yG",
      ".y..G",
      ".y.Gy",
      ".y.G.",
      ".y.GG",
      ".yGyy",
      ".yGy.",
      ".yGyG",
      ".yG.y",
      ".yG..",
      ".yG.G",
      ".yGGy",
      ".yGG.",
      ".yGGG",
      "..yyG",
      "..y.G",
      "..yGy",
      "..yG.",
      "..yGG",
      "...yG",
      "...Gy",
      "..Gyy",
      "..Gy.",
      "..GyG",
      "..G.y",
      "..GGy",
      ".Gyyy",
      ".Gyy.",
      ".GyyG",
      ".Gy.y",
      ".Gy..",
      ".Gy.G",
      ".GyGy",
      ".GyG.",
      ".GyGG",
      ".G.yy",
      ".G.y.",
      ".G.yG",
      ".G..y",
      ".G.Gy",
      ".GGyy",
      ".GGy.",
      ".GGyG",
      ".GG.y",
      ".GGGy",
      "Gyyy.",
      "Gyy.y",
      "Gyy..",
      "Gyy.G",
      "GyyG.",
      "Gy.yy",
      "Gy.y.",
      "Gy.yG",
      "Gy..y",
      "Gy...",
      "Gy..G",
      "Gy.Gy",
      "Gy.G.",
      "Gy.GG",
      "GyGy.",
      "GyG.y",
      "GyG..",
      "GyG.G",
      "GyGG.",
      "G.yyy",
      "G.yy.",
      "G.yyG",
      "G.y.y",
      "G.y..",
      "G.y.G",
      "G.yGy",
      "G.yG.",
      "G.yGG",
      "G..yy",
      "G..y.",
      "G..yG",
      "G...y",
      "G..Gy",
      "G.Gyy",
      "G.Gy.",
      "G.GyG",
      "G.G.y",
      "G.GGy",
      "GGyy.",
      "GGy.y",
      "GGy..",
      "GGy.G",
      "GGyG.",
      "GG.yy",
      "GG.y.",
      "GG.yG",
      "GG..y",
      "GG.Gy",
      "GGGy.",
      "GGG.y"
    };
    
    // When...
    List<WordPattern> patterns =
      Arrays.stream( clues)
      .map( WordPattern::new)
      .collect( toList());

    sort( patterns);
    

    // Then...
    List<String> sorted =
      patterns
      .stream()
      .map( WordPattern::toString)
      .collect( toList());
    
    assertThat(
      "Patterns", 
      sorted,
      contains(
        "GGGy.",
        "GGG.y",
        "GGyG.",
        "GGy.G",
        "GG.Gy",
        "GG.yG",
        "GyGG.",
        "GyG.G",
        "Gy.GG",
        "G.GGy",
        "G.GyG",
        "G.yGG",
        "yGGG.",
        "yGG.G",
        "yG.GG",
        "y.GGG",
        ".GGGy",
        ".GGyG",
        ".GyGG",
        ".yGGG",
        "GGyy.",
        "GGy.y",
        "GG.yy",
        "GyGy.",
        "GyG.y",
        "GyyG.",
        "Gyy.G",
        "Gy.Gy",
        "Gy.yG",
        "G.Gyy",
        "G.yGy",
        "G.yyG",
        "yGGy.",
        "yGG.y",
        "yGyG.",
        "yGy.G",
        "yG.Gy",
        "yG.yG",
        "yyGG.",
        "yyG.G",
        "yy.GG",
        "y.GGy",
        "y.GyG",
        "y.yGG",
        ".GGyy",
        ".GyGy",
        ".GyyG",
        ".yGGy",
        ".yGyG",
        ".yyGG",
        "GGy..",
        "GG.y.",
        "GG..y",
        "GyG..",
        "Gy.G.",
        "Gy..G",
        "G.Gy.",
        "G.G.y",
        "G.yG.",
        "G.y.G",
        "G..Gy",
        "G..yG",
        "yGG..",
        "yG.G.",
        "yG..G",
        "y.GG.",
        "y.G.G",
        "y..GG",
        ".GGy.",
        ".GG.y",
        ".GyG.",
        ".Gy.G",
        ".G.Gy",
        ".G.yG",
        ".yGG.",
        ".yG.G",
        ".y.GG",
        "..GGy",
        "..GyG",
        "..yGG",
        "Gyyy.",
        "Gyy.y",
        "Gy.yy",
        "G.yyy",
        "yGyy.",
        "yGy.y",
        "yG.yy",
        "yyGy.",
        "yyG.y",
        "yyyG.",
        "yyy.G",
        "yy.Gy",
        "yy.yG",
        "y.Gyy",
        "y.yGy",
        "y.yyG",
        ".Gyyy",
        ".yGyy",
        ".yyGy",
        ".yyyG",
        "Gyy..",
        "Gy.y.",
        "Gy..y",
        "G.yy.",
        "G.y.y",
        "G..yy",
        "yGy..",
        "yG.y.",
        "yG..y",
        "yyG..",
        "yy.G.",
        "yy..G",
        "y.Gy.",
        "y.G.y",
        "y.yG.",
        "y.y.G",
        "y..Gy",
        "y..yG",
        ".Gyy.",
        ".Gy.y",
        ".G.yy",
        ".yGy.",
        ".yG.y",
        ".yyG.",
        ".yy.G",
        ".y.Gy",
        ".y.yG",
        "..Gyy",
        "..yGy",
        "..yyG",
        "Gy...",
        "G.y..",
        "G..y.",
        "G...y",
        "yG...",
        "y.G..",
        "y..G.",
        "y...G",
        ".Gy..",
        ".G.y.",
        ".G..y",
        ".yG..",
        ".y.G.",
        ".y..G",
        "..Gy.",
        "..G.y",
        "..yG.",
        "..y.G",
        "...Gy",
        "...yG"));
    }
  }
