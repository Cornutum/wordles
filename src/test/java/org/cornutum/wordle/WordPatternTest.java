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
        "GGyy.",
        "GGy.G",
        "GGy.y",
        "GGy..",
        "GG.Gy",
        "GG.yG",
        "GG.yy",
        "GG.y.",
        "GG..y",
        "GyGG.",
        "GyGy.",
        "GyG.G",
        "GyG.y",
        "GyG..",
        "GyyG.",
        "Gyyy.",
        "Gyy.G",
        "Gyy.y",
        "Gyy..",
        "Gy.GG",
        "Gy.Gy",
        "Gy.G.",
        "Gy.yG",
        "Gy.yy",
        "Gy.y.",
        "Gy..G",
        "Gy..y",
        "Gy...",
        "G.GGy",
        "G.GyG",
        "G.Gyy",
        "G.Gy.",
        "G.G.y",
        "G.yGG",
        "G.yGy",
        "G.yG.",
        "G.yyG",
        "G.yyy",
        "G.yy.",
        "G.y.G",
        "G.y.y",
        "G.y..",
        "G..Gy",
        "G..yG",
        "G..yy",
        "G..y.",
        "G...y",
        "yGGG.",
        "yGGy.",
        "yGG.G",
        "yGG.y",
        "yGG..",
        "yGyG.",
        "yGyy.",
        "yGy.G",
        "yGy.y",
        "yGy..",
        "yG.GG",
        "yG.Gy",
        "yG.G.",
        "yG.yG",
        "yG.yy",
        "yG.y.",
        "yG..G",
        "yG..y",
        "yG...",
        "yyGG.",
        "yyGy.",
        "yyG.G",
        "yyG.y",
        "yyG..",
        "yyyG.",
        "yyy.G",
        "yy.GG",
        "yy.Gy",
        "yy.G.",
        "yy.yG",
        "yy..G",
        "y.GGG",
        "y.GGy",
        "y.GG.",
        "y.GyG",
        "y.Gyy",
        "y.Gy.",
        "y.G.G",
        "y.G.y",
        "y.G..",
        "y.yGG",
        "y.yGy",
        "y.yG.",
        "y.yyG",
        "y.y.G",
        "y..GG",
        "y..Gy",
        "y..G.",
        "y..yG",
        "y...G",
        ".GGGy",
        ".GGyG",
        ".GGyy",
        ".GGy.",
        ".GG.y",
        ".GyGG",
        ".GyGy",
        ".GyG.",
        ".GyyG",
        ".Gyyy",
        ".Gyy.",
        ".Gy.G",
        ".Gy.y",
        ".Gy..",
        ".G.Gy",
        ".G.yG",
        ".G.yy",
        ".G.y.",
        ".G..y",
        ".yGGG",
        ".yGGy",
        ".yGG.",
        ".yGyG",
        ".yGyy",
        ".yGy.",
        ".yG.G",
        ".yG.y",
        ".yG..",
        ".yyGG",
        ".yyGy",
        ".yyG.",
        ".yyyG",
        ".yy.G",
        ".y.GG",
        ".y.Gy",
        ".y.G.",
        ".y.yG",
        ".y..G",
        "..GGy",
        "..GyG",
        "..Gyy",
        "..Gy.",
        "..G.y",
        "..yGG",
        "..yGy",
        "..yG.",
        "..yyG",
        "..y.G",
        "...Gy",
        "...yG"));
    }
  }
