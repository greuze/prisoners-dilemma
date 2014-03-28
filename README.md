Prisoner's Dilemma in Java
==========================

# Introduction

Prisoner's dilemma game, to practice with Java and Scala. The purpose of
the game is to be in jail as few as possible, depending on your answer
to the Police and the other prisoner answer. More information about the
prisoner's dilemma itself can be found in
[wikipedia](https://en.wikipedia.org/wiki/Prisoner%27s_dilemma).

# Implementing a Prisoner

The user must only implement one class, that subclasses Prisoner, and
implements their abstract methods `giveAnswer` and
`notifyPoliceResponse`.

The `giveAnswer` method will tell the Police the
answer of the prisoner in every iteration (could and should be different
during the game).

When the Police have the answers from both prisoners,
they will inform the sentence to each one. These sentences could be:

* `FREE`. This prisioner talked, but the other did't. He goes free.
* `MINOR`. Both did't talk, so both have minor sentence (1 year in jail).
* `MEDIUM`. Both talked, so the sentence is medium for both (5 years).
* `MAJOR`. This prisoner did't talk, but the other betrayed him. Maximum
sentence of 10 years.

# Examples

There are two sample implemented prisoners, GentlePrisoner (that will
talk), and EvilPrisoner (that will always try to betray the other). Both
samples will always give the same response, as can be seen in
`giveAnswer` overriden method.

# Execution and results

First both prisoners must be run (if they are in the same machine, they
should use different ports). Both will show:

```
Waiting for Police...
```

When the Police is run, the contest will start, and at the end, some
statistics will be shown. To override default values (localhost
prisoners on ports 5000 and 5001, and 50 rounds), the arguments can be
passed to Police process:

```
java es.greuze.sandbox.prisoners.Police localhost 5000 localhost 5001 10
```

This is an example results with 10 rounds within GentlePrisoner and
EvilPrisoner:

```
From a total of 10 round:

Prisoner 1 results:
0 times free (0%)
0 times minor (0%)
0 times medium (0%)
10 times major (100%)
With a total of 100 years of jail

Prisoner 2 results:
10 times free (100%)
0 times minor (0%)
0 times medium (0%)
0 times major (0%)
With a total of 0 years of jail
```
