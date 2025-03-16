//////////////////////////////////////////////////////////////////////////////
// 
//                    Copyright 2023, Cornutum Project
//                             www.cornutum.org
//
//////////////////////////////////////////////////////////////////////////////

package org.cornutum.wordle;

import java.util.Optional;

/**
 * Represents a word pattern clue.
 */
public enum Clue
  {
    GREEN( "G", 0),
    YELLOW( "y", 1),
    WHITE( ".", 2);

    Clue( String text, int rank)
      {
      text_ = text;
      rank_ = rank;
      }

    public String toString()
     {
     return text_;
     }

    public static Clue valueOf( char c)
      {
      return
        Optional.ofNullable(
          c == 'G'?
          GREEN :

          c == 'y'?
          YELLOW :
          
          c == '.'?
          WHITE :
          
          null)
        
        .orElseThrow( () -> new IllegalArgumentException( String.format( "'%s' is not a valid Clue", c)));
      }

    public int getRank()
      {
      return rank_;
      }

    private final String text_;
    private final int rank_;
  }
