//////////////////////////////////////////////////////////////////////////////
// 
//                    Copyright 2023, Cornutum Project
//                             www.cornutum.org
//
//////////////////////////////////////////////////////////////////////////////

package org.cornutum.wordle;

import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Defines a set of pattern groups for specified guess word.
 */
public class WordPatternGroups implements Comparable<WordPatternGroups>
  {
  /**
   * Creates a new WordPatternGroups instance.
   */
  public WordPatternGroups( String guess)
    {
    guess_ = guess;
    }

  /**
   * Returns the guess word for these pattern groups.
   */
  public String getGuess()
    {
    return guess_;
    }

  /**
   * Returns the pattern groups for this guess word.
   */
  public Map<WordPattern,Set<String>> getGroups()
    {
    return groups_;
    }

  /**
   * Adds the pattern for the given word to groups for this guess.
   */
  public void addPattern( String word)
    {
    addPatternWord( WordPattern.patternFor( getGuess(), word), word);
    }

  /**
   * Compares {@link WordPatternGroups} instances.
   */
  public int compareTo( WordPatternGroups other)
    {
    return bySize.reversed().thenComparing( byVariance).compare( this, other);
    }

  /**
   * Adds the given word to the group for the given pattern.
   */
  private void addPatternWord( WordPattern pattern, String word)
    {
    Set<String> patternWords = Optional.ofNullable( getGroups().get( pattern)).orElse( new LinkedHashSet<String>());
    patternWords.add( word);
    getGroups().put( pattern, patternWords);
    }

  /**
   * Returns the variance in group size for all patterns.
   */
  private Double getVariance()
    {
    final double avgSize =
      getGroups().values().stream()
      .mapToDouble( words -> (double) words.size())
      .average()
      .orElse( 0.0);

    return
      getGroups().values().stream()
      .mapToDouble( words -> (double) words.size())
      .map( size -> Math.pow( size - avgSize, 2))
      .sum()
      / getGroups().size();
    }

  private final String guess_;
  private Map<WordPattern,Set<String>> groups_;

  private static final Comparator<WordPatternGroups> bySize = Comparator.comparing( wpg -> wpg.getGroups().size());
  private static final Comparator<WordPatternGroups> byVariance = Comparator.comparing( wpg -> wpg.getVariance());

  }
