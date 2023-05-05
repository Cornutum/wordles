//////////////////////////////////////////////////////////////////////////////
// 
//                    Copyright 2023, Cornutum Project
//                             www.cornutum.org
//
//////////////////////////////////////////////////////////////////////////////

package org.cornutum.wordle;

import org.cornutum.hamcrest.ExpectedFailure.Failable;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Optional;

/**
 * Base class for tests.
 */
public abstract class BaseTest
  {
  /**
   * Performs the given Failable, using the given standard input/output.
   * If <CODE>stdIn</CODE> is non-null, redirect standard input to read from the given stream.
   * If <CODE>stdOut</CODE> is non-null, redirect standard output to write to the given buffer.
   */
  protected void runWithStdIO( Failable failable, InputStream stdIn, StringBuffer stdOut)
    {
    InputStream prevIn = System.in;
    PrintStream prevOut = System.out;

    InputStream newIn = null;
    PrintStream newOut = null;
    ByteArrayOutputStream newOutBytes = null;
    
    try
      {
      if( stdIn != null)
        {
        System.setIn( (newIn = stdIn));
        }

      if( stdOut != null)
        {
        stdOut.delete( 0, stdOut.length());
        System.setOut( (newOut = new PrintStream( (newOutBytes = new ByteArrayOutputStream()))));
        }

      Optional<Throwable> failure = failable.get();
      if( failure.isPresent())
        {
        throw failure.get();
        }
      }
    catch( Throwable e)
      {
      throw new IllegalStateException( "Can't run", e);
      }
    finally
      {
      try
        {
        if( newIn != null)
          {
          newIn.close();
          }
        if( newOut != null)
          {
          newOut.close();
          }
        }
      catch( Exception ignore)
        {
        }

      System.setIn( prevIn);
      System.setOut( prevOut);

      if( newOutBytes != null)
        {
        stdOut.append( new String( newOutBytes.toByteArray(), Charset.forName( "UTF-8")));
        }
      }
    }

  /**
   * Performs the given Failable, using the given standard input/output.
   * If <CODE>stdIn</CODE> is non-null, redirect standard input to read from the given file.
   * If <CODE>stdOut</CODE> is non-null, redirect standard output to write to the given buffer.
   */
  protected void runWithStdIO( Failable failable, File stdIn, StringBuffer stdOut)
    {
    try
      {
      InputStream newIn =
        stdIn == null
        ? null
        : new FileInputStream( stdIn);
      runWithStdIO( failable, newIn, stdOut);
      }
    catch( Throwable e)
      {
      throw new IllegalStateException( String.format( "Can't read from file=%s", stdIn), e);
      }
    }

  /**
   * Performs the given Failable, using the given standard input/output.
   * If <CODE>stdIn</CODE> is non-null, redirect standard input to read from the given string.
   * If <CODE>stdOut</CODE> is non-null, redirect standard output to write to the given buffer.
   */
  protected void runWithStdIO( Failable failable, String stdIn, StringBuffer stdOut)
    {
    try
      {
      InputStream newIn =
        stdIn == null
        ? null
        : new ByteArrayInputStream( stdIn.getBytes( "UTF-8"));
      runWithStdIO( failable, newIn, stdOut);
      }
    catch( Throwable e)
      {
      throw new IllegalStateException( "Can't read from string", e);
      }
    }

  /**
   * Returns the location of the resource directory for this class.
   */
  protected File getResourceDir()
    {
    return getResourceDir( getClass());
    }
  
  /**
   * Return the file for the given resource.
   */
  protected File getResourceFile( String resource)
    {
    return getResourceFile( getClass(), resource);
    }

  /**
   * Returns the location of the resource directory for the given class.
   */
  protected File getResourceDir( Class<?> resourceClass)
    {
    URL classUrl = resourceClass.getResource( resourceClass.getSimpleName() + ".class");
    return new File( classUrl.getFile()).getParentFile();
    }
  
  /**
   * Return the file for the given resource.
   */
  protected File getResourceFile( Class<?> resourceClass, String resource)
    {
    return new File( getResourceDir( resourceClass), resource);
    }
  }
