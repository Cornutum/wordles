//////////////////////////////////////////////////////////////////////////////
// 
//                    Copyright 2025, Cornutum Project
//                             www.cornutum.org
//
//////////////////////////////////////////////////////////////////////////////

package org.cornutum.wordle;

import java.util.Comparator;
import java.util.List;
import static java.util.Collections.reverseOrder;
import static java.util.Collections.sort;

/**
 * Defines various methods of ranking {@link WordPatternGroups}
 */
public final class Rankings
  {
  /**
   * Creates a new Rankings instance.
   */
  private Rankings()
    {
    // Static methods only
    }

  /**
   * Rank by decreasing number of groups.
   */
  public static final Comparator<WordPatternGroups> bySize = reverseOrder( Comparator.comparing( wpg -> wpg.getSize()));

  /**
   * Rank by increasing variance of group size.
   */
  public static final Comparator<WordPatternGroups> byVariance = Comparator.comparing( wpg -> wpg.getVariance());

  /**
   * Rank by increasing maximum group size.
   */
  public static final Comparator<WordPatternGroups> byMaxGroup = Comparator.comparing( wpg -> wpg.getMax());

  /**
   * Rank alphabetically by guess word.
   */
  public static final Comparator<WordPatternGroups> byGuess = Comparator.comparing( wpg -> wpg.getGuess());

  /**
   * Rank by a combination of attributes.
   */
  public static final Comparator<WordPatternGroups> byCombined =
    bySize
    .thenComparing( byVariance)
    .thenComparing( byMaxGroup)
    .thenComparing( byGuess);


  /**
   * Returns the given elements ranked by the given comparator.
   */
  public static <T> List<T> ranked( List<T> elements, Comparator<T> comparator)
    {
    sort( elements, comparator);
    return elements;
    }
  }
