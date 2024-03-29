//////////////////////////////////////////////////////////////////////////////
// 
//                    Copyright 2023, Cornutum Project
//                             www.cornutum.org
//
//////////////////////////////////////////////////////////////////////////////

package org.cornutum.wordle;

import java.io.BufferedReader;
import java.io.File;
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
  public static class Options
    {
    /**
     * Creates a new Options object.
     */
    public Options()
      {
      setInteractive( false);
      setPrintAll( false);
      }

    /**
     * Creates a new Options object.
     */
    public Options( String[] args)
      {
      this();

      int i;

      // Handle options
      for( i = 0; i < args.length && args[i].charAt(0) == '-'; i = handleOption( args, i));

      // Handle additional arguments.
      handleArgs( args, i);
      }

    /**
     * Handles the i'th option and return the index of the next argument.
     */
    protected int handleOption( String[] args, int i)
      {
      String arg = args[i];

      if( arg.equals( "-help"))
        {
        throwHelpException();
        }

      else if( arg.equals( "-i"))
        {
        setInteractive( true);
        }

      else if( arg.equals( "-a"))
        {
        setPrintAll( true);
        }

      else
        {
        throwUsageException( String.format( "Unknown option: %s", arg));
        }

      return i + 1;
      }

    /**
     * Handles the non-option arguments i, i+1, ...
     */
    protected void handleArgs( String[] args, int i)
      {
      int nargs = args.length - i;

      if( nargs > 1)
        {
        throwUsageException( String.format( "Unexpected argument: %s", args[i+1]));
        }

      if( nargs > 0)
        {
        setWordFile( new File( args[i]));
        }
      }

    /**
     * Throws a HelpException after printing usage information to standard error.
     */
    protected void throwHelpException()
      {
      printUsage();
      throw new HelpException();
      }

    /**
     * Prints usage information to standard error.
     */
    protected void printUsage()
      {
      for( String line :
             new String[] {
               "Usage: wordles [option...] [wordFile]",
               "",
               "Analyzes a set of Wordle guess words. If a wordFile is specified, reads guess words from",
               "the given file. Otherwise, reads guess words from standard input",
               "",
               "Prints the results for the best guess to standard output. Results for other guesses can",
               "also be printed, depending on the given options.",
               "",
               "Each guess word is analyzed by comparing it to all of the other input words, assuming the",
               "other word is the actual Wordle target. The resulting matching clues are then used to",
               "organize all of the input words into groups. Printed results show all of the matching",
               "groups, along with the variance in group size, which is used in the ranking.",
               "",
               "Each option is one of the following:",
               "",
               "  -i   Interactive mode. Prompts for a new guess word and prints its results. For an empty",
               "       guess, prints the results of the next best guess from the input words. To quit, enter 'q'.",
               "",
               "  -a   Prints results for all input words in best-first order."
             })
        {
        System.err.println( line);
        }
      }

    /**
     * Changes if interactive mode is enabled.
     */
    public void setInteractive( boolean enabled)
      {
      interactive_ = enabled;
      }

    /**
     * Returns if interactive mode is enabled.
     */
    public boolean isInteractive()
      {
      return interactive_;
      }

    /**
     * Changes if printing all results.
     */
    public void setPrintAll( boolean enabled)
      {
      printAll_ = enabled;
      }

    /**
     * Returns if printing all results.
     */
    public boolean isPrintAll()
      {
      return printAll_;
      }

    /**
     * Changes the file containing guess words.
     */
    public void setWordFile( File wordFile)
      {
      wordFile_ = wordFile;
      }

    /**
     * Returns the file containing guess words.
     */
    public File getWordFile()
      {
      return wordFile_;
      }

    /**
     * Throws a IllegalArgumentException reporting a command line error.
     */
    private static void throwUsageException( String detail)
      {
      throwUsageException( detail, null);
      }

    /**
     * Throws a IllegalArgumentException reporting a command line error.
     */
    private static void throwUsageException( String detail, Exception cause)
      {
      throw getUsageException( detail, cause);
      }

    /**
     * Returns an IllegalArgumentException reporting a command line error.
     */
    private static IllegalArgumentException getUsageException( String detail, Exception cause)
      {
      return
        new IllegalArgumentException
        ( "Invalid command line argument. For all command line details, use the -help option.",
          new IllegalArgumentException( detail, cause));
      }
    
    @Override
    public String toString()
      {
      StringBuilder builder = new StringBuilder();

      if( isInteractive())
        {
        builder.append( " -i");
        }

      if( isPrintAll())
        {
        builder.append( " -a");
        }

      if( getWordFile() != null)
        {
        builder.append( " ").append( getWordFile());
        }
      
      return builder.toString();
      }

    private File wordFile_;
    private boolean interactive_;
    private boolean printAll_;
    }
  
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
      run( new Options( args));
      }
    catch( HelpException h)
      {
      exitCode = 1;
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
   * Analyzes a set of Wordle word choices, using the given options.
   */
  public static void run( Options options) throws Exception
    {
    Optional<File> wordFile = Optional.ofNullable( options.getWordFile());
    Wordles wordles = new Wordles( readWords( wordFile));
    List<WordPatternGroups> wordGroups = wordles.getWordPatternGroups();
    if( !wordGroups.isEmpty())
      {
      if( options.isPrintAll())
        {
        wordGroups.stream().forEach( wordGroup -> wordles.printWordPatternGroups( wordGroup));
        }
      else
        {
        int nextWord = 0;
        wordles.printWordPatternGroups( wordGroups.get( nextWord++));

        if( options.isInteractive())
          {
          if( !wordFile.isPresent())
            {
            System.err.println();
            System.err.println( "Warning: Can't use interactive mode when reading words from standard input.");
            }
          else
            {
            PrintWriter prompter = new PrintWriter( new OutputStreamWriter( System.out), true);
            BufferedReader reader = new BufferedReader( new InputStreamReader( System.in));

            boolean showMore = true;
            while( showMore)
              {
              prompter.print( "\nNext guess? ");
              prompter.flush();

              String nextGuess =
                Optional.ofNullable( reader.readLine())
                .map( String::trim)
                .filter( guess -> !guess.equalsIgnoreCase( "q"))
                .orElse( null);

              if( nextGuess == null || (nextGuess.isEmpty() && nextWord >= wordGroups.size()))
                {
                showMore = false;
                }
              else if( nextGuess.isEmpty())
                {
                wordles.printWordPatternGroups( wordGroups.get( nextWord++));
                }
              else
                {
                wordles.printWordPatternGroups( nextGuess);
                } 
              }
            }
          }
        }
      }
    }

  /**
   * Prints the word pattern groups for the given guess to standard output
   */
  public void printWordPatternGroups( String guess)
    {
    printWordPatternGroups( getWordPatternGroups( guess.trim().toUpperCase()), new OutputStreamWriter( System.out));
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
  protected static List<String> readWords( Optional<File> wordFile) throws IOException
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
  private static BufferedReader wordReader( Optional<File> wordFile) throws IOException
    {
    return
      new BufferedReader(
        wordFile.isPresent()
        ? new FileReader( wordFile.get())
        : new InputStreamReader( System.in));
    }

  private final List<String> words_;
  }
