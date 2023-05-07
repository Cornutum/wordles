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
    GREEN( "*"),
    YELLOW( "o"),
    BLACK( "_");

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
          c == '*'?
          GREEN :

          c == 'o'?
          YELLOW :
          
          c == '_'?
          BLACK :
          
          null)
        
        .orElseThrow( () -> new IllegalArgumentException( String.format( "'%s' is not a valid Clue", c)));
      }

    private final String text_;
  }
