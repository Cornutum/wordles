# Wordles: Find the best Wordle guess #

## What's New? ##

  * The latest version ([1.0.1](https://github.com/Cornutum/wordles/releases/tag/release-1.0.1))
    is now available at the [Maven Central Repository](https://central.sonatype.com/artifact/org.cornutum.wordle/wordles/1.0.1/versions).

  * For details on how to download and install the `wordles` command line interface, see [_How do I get it?_](#how-do-i-get-it).

## What does it do? ##

`wordles` is a Java program to help you play the online game of [Wordle](https://www.nytimes.com/games/wordle/index.html).

You're given six chances to guess the five-letter word of the day, based on a few clues from your previous guesses. At each
step, your first task is to think of words that match all of the clues. Hard! Then your next task is to pick a word that gives you the
best chance to find the solution before you run out of tries. Easy, if you use `wordles`!

Given a list of words, `wordles` will analyze the possible results and rank these words in order of preference. Each word
is analyzed by comparing it to all of the other input words, assuming that the other word is the actual Wordle target. The resulting
matching clues are then used to organize all of the input words into groups. Printed results show all of the matching groups,
along with the variance in group size, which is used in the ranking.


## How does it work? ##

`wordles` has a simple command line interface that you can run from a Unix shell. For details on how to download and install the `wordles` command,
see [_How do I get it?_](#how-do-i-get-it).

### Show the best word ###

`wordles` reads the word list from a file or from standard input. Here's a simple example.

```bash
echo "abuzz badly cocoa dogma gaudy laugh macho" | wordles
```

`wordles` then prints the result for the best guess word to standard output:

```
BADLY (0.000)
----------------

  .yy..
    DOGMA

  GGGGG
    BADLY

  yy...
    ABUZZ

  .y...
    COCOA

  .Gy.G
    GAUDY

  .G...
    MACHO

  .G.y.
    LAUGH
```

This means that, assuming these are all of the possible matching words, the best choice for your next guess should be
"BADLY". Why? Because even if you're wrong, you should be able to get the answer on your next guess. That's because Wordle will
show you a different set of clues for each of the other words.  Even if your next guess is wrong, your chances of finding the
solution are the same, no matter which clues you see. In other words, the variance in the size of all the possible matching
groups is 0.00.

`wordles` shows you a simple text representation of the green, yellow, and white squares that the Wordle game displays as
clues. For example, if you guess "BADLY" but the actual target word is "LAUGH", Wordle will display
:white_large_square::green_square::white_large_square::yellow_square::white_large_square:, which is shown by `wordles` as
".G.y.".

### Show the results for all words ###

Let's suppose all of the words from the previous example are listed in a file named `myWords`. Then you can print the results
for all of these words with the following command.

```bash
wordles -a myWords
```

`wordles` then prints the results for all words in best-first order. As you can see the worst choice is "COCOA", which has a
high variance (1.688).  If you guessed "COCOA" and produced a
:white_large_square::white_large_square::white_large_square::yellow_square: result, it would probably take two or three more
guesses to find the answer!

```
BADLY (0.000)
----------------

  .yy..
    DOGMA
...

COCOA (1.688)
----------------

  ....y
    ABUZZ
    BADLY
    GAUDY
    LAUGH

  .yG.y
    MACHO

  .G..G
    DOGMA

  GGGGG
    COCOA
```

### Show results interactively ###

If you run `wordles` in interactive mode, you can enter any five-letter word you like and see its results when compared with the
input word list. To see the next best choice from the input word list, just enter an empty word. To exit interactive mode, enter
"q".

You can use interactive mode only if the words come from a file. For example, try this command:

```bash
wordles -i myWords
```

Then you'll see something like this:

```
BADLY (0.000)
----------------

  .yy..
    DOGMA
...

Next guess? macho


MACHO (0.139)
----------------

  .G...
    BADLY
    GAUDY
...

Next guess? elbow


ELBOW (0.139)
----------------

  ...y.
    DOGMA
    MACHO
...

Next guess? 


DOGMA (0.000)
----------------

  y.y.y
    GAUDY
...

Next guess? q
```


### How do I get it? ###

To get the command line version of `wordles`, download the `wordles` shell distribution file from the Maven Central Repository, using the following procedure.

  1. Visit the [Central Repository page](https://central.sonatype.com/artifact/org.cornutum.wordle/wordles/1.0.1/versions) for `wordles`.
  1. Find the entry for the latest version and click on "Browse".
  1. To download the shell distribution ZIP file, click on "wordles-_${version}_.zip". If you prefer a compressed TAR file, click on "wordles-_${version}_.tar.gz".

Extract the contents of the distribution file to any directory you like -- this is now your _"wordles home directory"_. Unpacking
the distribution file will create a _"wordles release directory"_ -- a subdirectory of the form `wordles-shell-`_m_._n_._r_ -- that
contains all the files for this release of `wordles`. The release directory contains the following subdirectories.

  * `bin`: Executable shell scripts
  * `lib`: All JAR files needed to run `wordles`

One more step and you're ready to go: add the path to the `bin` subdirectory to the `PATH` environment variable for your system.
