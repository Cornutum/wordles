//////////////////////////////////////////////////////////////////////////////
// 
//                    Copyright 2023, Cornutum Project
//                             www.cornutum.org
//
//////////////////////////////////////////////////////////////////////////////

package org.cornutum.wordle;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Analyzes a set of Wordle word choices.
 */
public class Wordles
  {
  /**
   * Creates a new Wordles object.
   */
  private Wordles()
    {
    // Static methods only
    }

  /**
   * Analyzes a set of Wordle word choices.
   */
  public static void main( String[] args)
    {
    int exitCode = 0;
    try
      {
      if( args.length > 1)
        {
        System.err.println( "Unexpected argument");
        exitCode = 1;
        }
      else
        {
        try( BufferedReader reader = wordReader( args.length == 1 ? args[0] : null))
          {
          List<String> words = readWords( reader);
          }
        }
      }
    catch( Throwable e)
      {
      exitCode = 1;
      e.printStackTrace( System.err);
      }
    finally
      {
      System.exit( exitCode);
      }
    }

  /**
   * Reads a list of words.
   */
  protected static List<String> readWords( BufferedReader reader) throws IOException
    {
    List<String> words = new ArrayList<String>();

    for( String nextLine = reader.readLine(); nextLine != null; nextLine = reader.readLine())
      {
      for( String nextWord : nextLine.trim().split( "\\s+"))
        {
        if( !nextWord.isEmpty())
          {
          if( nextWord.length() != 5)
            {
            throw new IllegalArgumentException( String.format( "'%s' is not a 5-letter word", nextWord));
            }
          if( !IntStream.range( 0, 5).map( i -> (int) nextWord.charAt( i)).allMatch( Character::isAlphabetic))
            {
            throw new IllegalArgumentException( String.format( "'%s' contains non-alphabetic chars", nextWord));
            }
          
          words.add( nextWord.toUpperCase());
          }
        }
      }
    
    return words;
    }

  /**
   * Returns a reader for the contents of the given word file
   */
  protected static BufferedReader wordReader( String wordFile) throws IOException
    {
    return
      new BufferedReader(
        wordFile == null
        ? new InputStreamReader( System.in)
        : new FileReader( wordFile));
    }
  }
