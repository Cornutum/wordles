//////////////////////////////////////////////////////////////////////////////
// 
//                    Copyright 2023, Cornutum Project
//                             www.cornutum.org
//
//////////////////////////////////////////////////////////////////////////////

package org.cornutum.wordle;

import org.junit.Test;
import static org.cornutum.hamcrest.Composites.*;
import static org.hamcrest.MatcherAssert.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static java.util.stream.Collectors.toList;

/**
 * Runs tests for {@link Worldes}.
 */
public class WordlesTest extends BaseTest
  {  
  @Test
  public void whenWordIsGuppy() throws Exception
    {
    // Given...
    List<String> words = Wordles.readWords( Optional.of( getResourceFile( "words-guppy.txt")));
    Wordles wordles = new Wordles( words);

    // When...
    List<WordPatternGroups> patternGroups =
      Rankings.ranked(
        wordles.getWordPatternGroups(),
        Rankings.byCombined);

    // Then...
    List<String> guesses = patternGroups.stream().map( WordPatternGroups::getGuess).collect( toList());
    assertThat(
      "Guesses",
      guesses,
      listsMembers(
        Arrays.asList(
          "GUPPY",
          "BUMPY",
          "GUMMY",
          "PINUP",
          "JUMPY",
          "BUNNY",
          "PUPPY",
          "BUGGY",
          "UNZIP",
          "MUMMY",
          "FUNNY",
          "UNIFY")));

    WordPatternGroups best = patternGroups.get(0);
    List<String> bestPatterns = best.getGroups().keySet().stream().map( String::valueOf).collect( toList());
    assertThat(
      "Patterns",
      bestPatterns,
      containsMembers(
        Arrays.asList(
          ".yy..",
          ".G..G",
          ".GGGG",
          "yG..G",
          "GG..G",
          "GGGGG",
          ".yyy.",
          ".G.GG",
          ".y..G")));

    assertThat( ".yy..", best.getGroups().get( WordPattern.valueOf( ".yy..")), containsMembers( Arrays.asList( "UNZIP")));
    assertThat( ".G..G", best.getGroups().get( WordPattern.valueOf( ".G..G")), containsMembers( Arrays.asList( "BUNNY", "FUNNY", "MUMMY")));
    assertThat( ".GGGG", best.getGroups().get( WordPattern.valueOf( ".GGGG")), containsMembers( Arrays.asList( "PUPPY")));
    assertThat( "yG..G", best.getGroups().get( WordPattern.valueOf( "yG..G")), containsMembers( Arrays.asList( "BUGGY")));
    assertThat( "GG..G", best.getGroups().get( WordPattern.valueOf( "GG..G")), containsMembers( Arrays.asList( "GUMMY")));
    assertThat( "GGGGG", best.getGroups().get( WordPattern.valueOf( "GGGGG")), containsMembers( Arrays.asList( "GUPPY")));
    assertThat( ".yyy.", best.getGroups().get( WordPattern.valueOf( ".yyy.")), containsMembers( Arrays.asList( "PINUP")));
    assertThat( ".G.GG", best.getGroups().get( WordPattern.valueOf( ".G.GG")), containsMembers( Arrays.asList( "BUMPY", "JUMPY")));
    assertThat( ".y..G", best.getGroups().get( WordPattern.valueOf( ".y..G")), containsMembers( Arrays.asList( "UNIFY")));
    }
  }
