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
    List<String> words = Wordles.readWords( Optional.of( getResourceFile( "words-guppy.txt").getPath()));
    Wordles wordles = new Wordles( words);

    // When...
    List<WordPatternGroups> patternGroups = wordles.getWordPatternGroups();

    // Then...
    List<String> guesses = patternGroups.stream().map( WordPatternGroups::getGuess).collect( toList());
    assertThat(
      "Guesses",
      guesses,
      listsMembers(
        Arrays.asList(
          "BUMPY",
          "GUMMY",
          "GUPPY",
          "JUMPY",
          "PINUP",
          "UNZIP",
          "BUGGY",
          "BUNNY",
          "MUMMY",
          "PUPPY",
          "FUNNY",
          "UNIFY")));

    WordPatternGroups best = patternGroups.get(0);
    List<String> bestPatterns = best.getGroups().keySet().stream().map( String::valueOf).collect( toList());
    assertThat(
      "Patterns",
      bestPatterns,
      containsMembers(
        Arrays.asList(
          "o___o",
          "o___*",
          "*****",
          "_*_**",
          "o__o_",
          "_*__*",
          "_****",
          "**__*",
          "_**_*")));

    assertThat( "o___o", best.getGroups().get( WordPattern.valueOf( "o___o")), containsMembers( Arrays.asList( "UNZIP")));
    assertThat( "o___*", best.getGroups().get( WordPattern.valueOf( "o___*")), containsMembers( Arrays.asList( "UNIFY")));
    assertThat( "*****", best.getGroups().get( WordPattern.valueOf( "*****")), containsMembers( Arrays.asList( "BUMPY")));
    assertThat( "_*_**", best.getGroups().get( WordPattern.valueOf( "_*_**")), containsMembers( Arrays.asList( "GUPPY", "PUPPY")));
    assertThat( "o__o_", best.getGroups().get( WordPattern.valueOf( "o__o_")), containsMembers( Arrays.asList( "PINUP")));
    assertThat( "_*__*", best.getGroups().get( WordPattern.valueOf( "_*__*")), containsMembers( Arrays.asList( "FUNNY")));
    assertThat( "_****", best.getGroups().get( WordPattern.valueOf( "_****")), containsMembers( Arrays.asList( "JUMPY")));
    assertThat( "**__*", best.getGroups().get( WordPattern.valueOf( "**__*")), containsMembers( Arrays.asList( "BUGGY", "BUNNY")));
    assertThat( "_**_*", best.getGroups().get( WordPattern.valueOf( "_**_*")), containsMembers( Arrays.asList( "GUMMY", "MUMMY")));
    }
  }
