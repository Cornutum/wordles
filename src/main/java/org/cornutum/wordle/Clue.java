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
    GREEN( "G"),
    YELLOW( "y"),
    BLACK( ".");

    Clue( String text)
      {
      text_ = text;
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
          BLACK :
          
          null)
        
        .orElseThrow( () -> new IllegalArgumentException( String.format( "'%s' is not a valid Clue", c)));
      }

    private final String text_;
  }
