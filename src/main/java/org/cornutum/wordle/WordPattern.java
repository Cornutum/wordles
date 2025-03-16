//////////////////////////////////////////////////////////////////////////////
// 
//                    Copyright 2023, Cornutum Project
//                             www.cornutum.org
//
//////////////////////////////////////////////////////////////////////////////

package org.cornutum.wordle;

import static org.cornutum.wordle.Clue.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;
import static java.util.Collections.emptyList;
import static java.util.Collections.reverseOrder;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

/**
 * Defines matching pattern for a given guess.
 */
public class WordPattern implements Comparable<WordPattern>
  {
  /**
   * Creates a new WordPattern instance.
   */
  public WordPattern( List<Clue> clues)
    {
    clues_ =
      Optional.ofNullable( clues)
      .filter( list -> list.size() == 5)
      .orElseThrow( () -> new IllegalArgumentException( "A WordPattern must contain exactly 5 clues."));
    }

  /**
   * Creates a new WordPattern instance.
   */
  public WordPattern( Clue... clues)
    {
    this( Arrays.asList( clues));
    }

  /**
   * Creates a new WordPattern instance.
   */
  public WordPattern( String clues)
    {
    this(
      IntStream.range( 0, Optional.ofNullable( clues).map( String::length).orElse( 0))
      .mapToObj( i -> Clue.valueOf( clues.charAt( i)))
      .collect( toList()));
    }

  /**
   * Returns the pattern comparing the given guess to the given target word.
   */
  public static WordPattern patternFor( String target, String guess)
    {
    Clue[] clues = new Clue[]{ WHITE, WHITE, WHITE, WHITE, WHITE};

    Map<Integer,List<Integer>> targetCharPos =
      IntStream.range( 0, 5)
      .mapToObj( Integer::valueOf)
      .collect( groupingBy( i -> Integer.valueOf( target.charAt( i))));

    Map<Integer,List<Integer>> guessCharPos =
      IntStream.range( 0, 5)
      .mapToObj( Integer::valueOf)
      .collect( groupingBy( i -> Integer.valueOf( guess.charAt( i))));

    for( Integer guessChar : guessCharPos.keySet())
      {
      List<Integer> targetPos = Optional.ofNullable( targetCharPos.get( guessChar)).orElse( emptyList());
      int guessCharCount = targetPos.size();
      if( guessCharCount > 0)
        {
        List<Integer> guessPos = guessCharPos.get( guessChar);
        Map<Clue,List<Integer>> guessClues = guessPos.stream().collect( groupingBy( pos -> targetPos.contains( pos)? GREEN : YELLOW));

        List<Integer> greenPos = Optional.ofNullable( guessClues.get( GREEN)).orElse( emptyList());
        for( int i = 0;

             guessCharCount > 0
               && i < greenPos.size();

             clues[ greenPos.get(i)] = GREEN,
               guessCharCount--,
               i++);

        List<Integer> yellowPos = Optional.ofNullable( guessClues.get( YELLOW)).orElse( emptyList());
        Collections.sort( yellowPos);
        for( int i = 0;

             guessCharCount > 0
               && i < yellowPos.size();

             clues[ yellowPos.get(i)] = YELLOW,
               guessCharCount--,
               i++);
        }
      }

    return new WordPattern( Arrays.asList( clues));
    }

  /**
   * Compares {@link WordPattern} instances.
   */
  public int compareTo( WordPattern other)
    {
    return patternComparator_.compare( this, other);
                      
    }

  public boolean equals( Object object)
    {
    WordPattern other =
      object != null && object.getClass().equals( getClass())
      ? (WordPattern) object
      : null;

    return
      other != null
      && other.clues_.equals( clues_);
    }

  public int hashCode()
    {
    return
      getClass().hashCode()
      ^ clues_.hashCode();
    }

  public String toString()
    {
    return clues_.stream().map( Clue::toString).collect( joining());
    }

  /**
   * Returns the WordPattern represented by the given string.
   */
  public static WordPattern valueOf( String pattern)
    {
    return
      new WordPattern(
        IntStream.range( 0, pattern.length())
        .mapToObj( i -> Clue.valueOf( pattern.charAt( i)))
        .collect( toList()));
    }

  private final List<Clue> clues_;

  private static final Comparator<WordPattern> byClues_ =
    byClue( 0)
    .thenComparing( byClue( 1))
    .thenComparing( byClue( 2))
    .thenComparing( byClue( 3))
    .thenComparing( byClue( 4));

  private static Comparator<WordPattern> byClue( int index)
    {
    return
      Comparator.comparing(
        wp -> wp.clues_.get( index),
        Comparator.comparingInt( Clue::getRank));
    }
  
  private static final Comparator<WordPattern> byClueCounts_ =
    byClueCount( GREEN)
    .thenComparing( byClueCount( YELLOW))
    .thenComparing( byClueCount( WHITE));

  private static Comparator<WordPattern> byClueCount( Clue clue)
    {
    return reverseOrder( Comparator.comparingInt( wp -> wp.clueCount( clue)));
    }

  private int clueCount( Clue clue)
    {
    return (int) clues_.stream().filter( c -> c == clue).count();
    }

  private static final Comparator<WordPattern> patternComparator_ =
    byClueCounts_
    .thenComparing( byClues_);
  }
