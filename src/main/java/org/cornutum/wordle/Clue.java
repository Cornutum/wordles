//////////////////////////////////////////////////////////////////////////////
// 
//                    Copyright 2023, Cornutum Project
//                             www.cornutum.org
//
//////////////////////////////////////////////////////////////////////////////

package org.cornutum.wordle;

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

    private final String text_;
  }
