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
import java.util.Optional;
import java.util.stream.IntStream;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

/**
 * Analyzes a set of Wordle word choices.
 */
public class Wordles
  {
  /**
   * Creates a new Wordles object.
   */
  public Wordles()
    {
    this( null);
    }
  
  /**
   * Creates a new Wordles object.
   */
  public Wordles( List<String> words)
    {
    words_ = Optional.ofNullable( words).orElse( emptyList()).stream().collect( toList());
    }

  /**
   * Returns the word list to be analyzed.
   */
  public List<String> getWords()
    {
    return words_;
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
          Wordles wordles = new Wordles( readWords( reader));
          List<WordPatternGroups> wordGroups = wordles.getWordPatternGroups();
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
   * Returns the word pattern groups for each member of this word list.
   */
  public List<WordPatternGroups> getWordPatternGroups()
    {
    return
      getWords().stream()
      .map( word -> getWordPatternGroups( word))
      .collect( toList());
    }

  /**
   * Returns the word pattern groups for the given guess
   */
  protected WordPatternGroups getWordPatternGroups( String guess)
    {
    WordPatternGroups groups = new WordPatternGroups( guess);

    for( String word : getWords())
      {
      groups.addPattern( word);
      }
    
    return groups;
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

  private final List<String> words_;
  }
