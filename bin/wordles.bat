@echo off
rem #######################################################################
rem #
rem #                     Copyright 2026, Cornutum Project
rem #                              www.cornutum.org
rem # 
rem #######################################################################

set WORDLES_HOME=%~dp0..
set WORDLES_LIB=%WORDLES_HOME%\lib

java -cp "%WORDLES_LIB%"\wordles-${project.version}.jar org.cornutum.wordle.Wordles %*
