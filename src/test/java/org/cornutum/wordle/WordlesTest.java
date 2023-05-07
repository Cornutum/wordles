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
          "_oo__",
          "_*__*",
          "_****",
          "o*__*",
          "**__*",
          "*****",
          "_ooo_",
          "_*_**",
          "_o__*")));

    assertThat( "_oo__", best.getGroups().get( WordPattern.valueOf( "_oo__")), containsMembers( Arrays.asList( "UNZIP")));
    assertThat( "_*__*", best.getGroups().get( WordPattern.valueOf( "_*__*")), containsMembers( Arrays.asList( "BUNNY", "FUNNY", "MUMMY")));
    assertThat( "_****", best.getGroups().get( WordPattern.valueOf( "_****")), containsMembers( Arrays.asList( "PUPPY")));
    assertThat( "o*__*", best.getGroups().get( WordPattern.valueOf( "o*__*")), containsMembers( Arrays.asList( "BUGGY")));
    assertThat( "**__*", best.getGroups().get( WordPattern.valueOf( "**__*")), containsMembers( Arrays.asList( "GUMMY")));
    assertThat( "*****", best.getGroups().get( WordPattern.valueOf( "*****")), containsMembers( Arrays.asList( "GUPPY")));
    assertThat( "_ooo_", best.getGroups().get( WordPattern.valueOf( "_ooo_")), containsMembers( Arrays.asList( "PINUP")));
    assertThat( "_*_**", best.getGroups().get( WordPattern.valueOf( "_*_**")), containsMembers( Arrays.asList( "BUMPY", "JUMPY")));
    assertThat( "_o__*", best.getGroups().get( WordPattern.valueOf( "_o__*")), containsMembers( Arrays.asList( "UNIFY")));
    }
  }
