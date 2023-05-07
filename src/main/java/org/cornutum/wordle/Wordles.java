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
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
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
        Optional<String> wordFile = Optional.ofNullable( args.length == 1 ? args[0] : null);
        Wordles wordles = new Wordles( readWords( wordFile));
        List<WordPatternGroups> wordGroups = wordles.getWordPatternGroups();
        if( !wordGroups.isEmpty())
          {
          wordles.printWordPatternGroups( wordGroups.get(0));

          if( wordFile.isPresent())
            {
            PrintWriter prompter = new PrintWriter( new OutputStreamWriter( System.out), true);
            BufferedReader reader = new BufferedReader( new InputStreamReader( System.in));
            boolean showMore = true;
            while( showMore)
              {
              prompter.print( "\nNext guess? ");
              prompter.flush();

              Optional<String> nextGuess =
                Optional.ofNullable( reader.readLine())
                .map( String::trim)
                .filter( guess -> !guess.isEmpty());

              nextGuess.ifPresent( guess -> wordles.printWordPatternGroups( guess));
              showMore = nextGuess.isPresent();            
              }
            }
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
   * Prints the word pattern groups for the given guess to standard output
   */
  public void printWordPatternGroups( String guess)
    {
    WordPatternGroups patternGroups = getWordPatternGroups( guess.trim().toUpperCase());
    if( patternGroups == null)
      {
      System.out.println( String.format( "No pattern groups found for '%s'", guess));
      }
    else
      {
      printWordPatternGroups( patternGroups, new OutputStreamWriter( System.out));
      }
    }

  /**
   * Prints the given word pattern groups to standard output
   */
  public void printWordPatternGroups( WordPatternGroups patternGroups)
    {
    printWordPatternGroups( patternGroups, new OutputStreamWriter( System.out));
    }

  /**
   * Prints the given word pattern groups to the given stream.
   */
  public void printWordPatternGroups( WordPatternGroups patternGroups, Writer stream)
    {
    PrintWriter writer = new PrintWriter( stream, true);
    writer.println();
    writer.println();
    writer.println( String.format( "%s (%.3f)", patternGroups.getGuess(), patternGroups.getVariance()));
    writer.println( "----------------");
    patternGroups.getGroups().entrySet().stream()
      .sorted( (e1, e2) -> e2.getValue().size() - e1.getValue().size())
      .forEach( e -> {
        writer.println();
        writer.println( String.format( "  %s", e.getKey()));
        for( String word : e.getValue())
          {
          writer.println( String.format( "    %s", word));
          }
        });
    }

  /**
   * Returns the word pattern groups for each member of this word list.
   */
  public List<WordPatternGroups> getWordPatternGroups()
    {
    return
      getWords().stream()
      .map( word -> getWordPatternGroups( word))
      .sorted()
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
  protected static List<String> readWords( Optional<String> wordFile) throws IOException
    {
    try( BufferedReader reader = wordReader( wordFile))
      {
      return readWords( reader);
      }
    }

  /**
   * Reads a list of words.
   */
  private static List<String> readWords( BufferedReader reader) throws IOException
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
  private static BufferedReader wordReader( Optional<String> wordFile) throws IOException
    {
    return
      new BufferedReader(
        wordFile.isPresent()
        ? new FileReader( wordFile.get())
        : new InputStreamReader( System.in));
    }

  private final List<String> words_;
  }
