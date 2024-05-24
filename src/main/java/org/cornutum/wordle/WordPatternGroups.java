//////////////////////////////////////////////////////////////////////////////
// 
//                    Copyright 2023, Cornutum Project
//                             www.cornutum.org
//
//////////////////////////////////////////////////////////////////////////////

package org.cornutum.wordle;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import static java.util.stream.Collectors.joining;

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
    groups_ = new HashMap<WordPattern,Set<String>>();
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
    addPatternWord( WordPattern.patternFor( word, getGuess()), word);
    }

  /**
   * Compares {@link WordPatternGroups} instances.
   */
  public int compareTo( WordPatternGroups other)
    {
    return
      bySize.reversed()
      .thenComparing( byVariance)
      .thenComparing( byMaxGroup)
      .thenComparing( byGuess)
      .compare( this, other);
    }

  public String toString()
    {
    return
      new StringBuilder( getClass().getSimpleName())
      .append( '[')
      .append( getGuess())
      .append( ',')
      .append( String.format( "%.3f", getVariance()))
      .append( ',')
      .append(
        getGroups().entrySet().stream()
        .map( e -> String.format( "%s=%d", e.getKey(), e.getValue().size()))
        .collect( joining( ",")))
      .append( ']')
      .toString();
    }
  
  /**
   * Adds the given word to the group for the given pattern.
   */
  private void addPatternWord( WordPattern pattern, String word)
    {
    Set<String> patternWords = Optional.ofNullable( getGroups().get( pattern)).orElse( new TreeSet<String>());
    patternWords.add( word);
    getGroups().put( pattern, patternWords);
    }

  /**
   * Returns the number of pattern groups.
   */
  public int getSize()
    {
    return getGroups().size();
    }

  /**
   * Returns the number of words in the largests pattern group.
   */
  public int getMax()
    {
    return getGroups().values().stream().mapToInt( Set::size).max().orElse( 0);
    }

  /**
   * Returns the variance in group size for all patterns.
   */
  public Float getVariance()
    {
    final double avgSize =
      getGroups().values().stream()
      .mapToDouble( words -> (double) words.size())
      .average()
      .orElse( 0.0);

    return
      (float)
      getGroups().values().stream()
      .mapToDouble( words -> (double) words.size())
      .map( size -> Math.pow( size - avgSize, 2))
      .average()
      .orElse( 0.0);
    }

  private final String guess_;
  private final Map<WordPattern,Set<String>> groups_;

  private static final Comparator<WordPatternGroups> bySize = Comparator.comparing( wpg -> wpg.getSize());
  private static final Comparator<WordPatternGroups> byVariance = Comparator.comparing( wpg -> wpg.getVariance());
  private static final Comparator<WordPatternGroups> byMaxGroup = Comparator.comparing( wpg -> wpg.getMax());
  private static final Comparator<WordPatternGroups> byGuess = Comparator.comparing( wpg -> wpg.getGuess());
  }
