# Contributing to Ballerina

Ballerina is an open source programming language and platform for cloud-era application programmers to easily write software that just works.

We are an open-source project under Apache license and the work of hundreds of contributors.

We appreciate your help!

## Getting Started

* Download Ballerina at [https://ballerina.io](https://ballerina.io) and go through the getting started tutorials at [https://ballerina.io/learn](https://ballerina.io/learn)
* Read our [Code of Conduct](CODE_OF_CONDUCT.md)
* Join the conversations at:
   * [StackOverflow](https://stackoverflow.com/questions/tagged/ballerina): to get help with Ballerina; use Ballerina tag for any your questions there,
   * [Slack](https://ballerinalang.slack.com/): for real-time discussions with the team and community,
   * [Ballerina-Dev Google Group](https://groups.google.com/forum/#!forum/ballerina-dev): developer team mailing list to discuss Ballerina roadmap, features and issues on the works, and so on,
   * [GitHub](https://github.com/ballerina-platform/ballerina-lang/issues): file issues, comment on other issues, send your pull requests. 
* Submit issues:
   * Found a security flaw? Please email security@ballerina.io
   * Submitting a bug is just as important as contributing code. Go to the Issues tab of the GitHub repo and click the New Issue button to file a bug report.
* Start with easy fix issues:
   * Browse issues labeled easyfix,
   * Use comments on the issue itself to indicate that you will be working on it and get guidance and help.

## Communicating with the team

[Ballerina-Dev Google Group](https://groups.google.com/forum/#!forum/ballerina-dev) is the main Ballerina project discussion forum.

[StackOverflow](https://stackoverflow.com/questions/tagged/ballerina) is used for support, [Slack](https://ballerinalang.slack.com/) for real-time communications, and [GitHub](https://github.com/ballerina-platform/ballerina-lang/issues) for issues and code repositories.

## Filing issues

If you are unsure whether you have found a bug, please consider searching existing issues in github and asking in Ballerina-Dev Google Group.

> IMPORTANT: Sensitive security-related issues should be reported to [security@ballerina.io](security@ballerina.io). See the security policy for details.

To file non-security issues:

1. Click the **Issues** tab in the github repository,

2. Click the **New Issue** button,

3. Fill out all sections in the issue template and submit.

## Contributing site changes, docs, and examples

## Contributing code

### Accepting Contributor License Agreement (CLA)

Before you submit your first contribution please accept our Contributor License Agreement (CLA) here. When you send your first Pull Request (PR), GitHub will ask you to accept the CLA.

There is no need to do this before you send your first PR.

Subsequent PRs will not require CLA acceptance.

If for some (unlikely) reason at any time CLA changes, you will get presented with the new CLA text on your first PR after the change.

### Obtaining the Source Code and Building the Project

#### Build Prerequisites

**Prerequisite 1** - Install a Java Development Kit (JDK) version 8

Building Ballerina requires a Java SE Development Kit (JDK) version 8 to be installed. You can download one from one of the following locations:

* [http://www.oracle.com/technetwork/java/javase/downloads/index.html](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
* [http://openjdk.java.net/install/index.html](http://openjdk.java.net/install/index.html)

> NOTE: Set an environment variable `JAVA_HOME` to the path name of the directory into which you installed the JDK release.

**Prerequisite 2** - Node (v8.9.x or latest LTS release) + npm (v5.6.0 or later)

**Prerequisite 3** - Docker 

#### Building from the source

Clone this repository using the following command.

```
git clone --recursive https://github.com/ballerina-platform/ballerina-lang.git
```

If you have forked the repository to your github account then use the following command replacing <YOUR-GITHUB-USERNAME> with your git username.

````
git clone --recursive https://github.com/<YOUR-GITHUB-USERNAME>/ballerina-lang.git
````

If you download the sources, you need to update the git submodules using the following command.

```
git submodule update --init 
```

Run the gradle command `./gradlew clean build` from the Ballerina root directory:

Extract the Ballerina distribution created at `distribution/zip/ballerina/target/ballerina-<version>-SNAPSHOT.zip`. The `zip/ballerina` directory contains the runtime only. `zip/ballerina-tools/` contains the runtime and tools (e.g., Ballerina Composer).

Note: It is possible to face an IOException error stating "Too many open files". This is due to the default number of possible open files being set to a lower number on your operating system than required for Ballerina to be compiled. You may have to increase the number of open files/file descriptors (FD) on your operating system. This is OS dependent and you can search the internet on how to do this for your operating system (Windows/OSX/Linux). You could update the maximum number of open files to 1000000 (or higher).

### Setting up your development environment

#### Setup IntelliJ IDEA

##### Importing a Ballerina Project

Import the Ballerina project to IntelliJ IDEA similar to any other Gradle project. 

* Navigate to `File | Open` menu.
* Browse the filesystem and Select Ballerina Project's root directory.
   * If prompted to import Gradle project select `Auto import`.
   * Set Project SDK as Java 1.8

##### Useful IDEA Plugins

* ANTLR 4 - https://plugins.jetbrains.com/plugin/7358-antlr-v4-grammar-plugin 
* Ballerina - https://plugins.jetbrains.com/plugin/9520-ballerina (Need IDEA 2016.3 or newer)

#### Setup Eclipse

##### Importing a Ballerina Project

All Ballerina repositories are developed as Maven or Gradle projects. So you can import any Ballerina project to Eclipse similar to any Maven or Gradle project. 

* Navigate to **File** → **Import..**
* Select **Existing Maven Projects** under **Maven**
* Next, browse the file system, and open the Maven module.

##### Installing ANTLR4 plugin
If you are working with the grammar, it would be useful to have ANTLR 4 IDE plugin installed for Eclipse. To install this using Eclipse marketplace:

1. Navigate to **Help** → **Eclipse Marketplace**

2. Search for **antlr4**

3. Install ANTLR 4 IDE plugin (which supports antlr 4.x version).

To open the plugin views: 

1. Navigate to **Window** → **Show View** → **Other**. 

2. Under **ANTLR4** section, select and enable **Parse Tree** view and **Syntax Diagram** views.

### Working With the Ballerina Grammar

Ballerina grammar has been implemented using ANTLR4. It is recommended to have some basic understanding on ANTLR4 grammar syntax and concepts, before working with the Ballerina grammar. “Parr, Terence (January 15, 2013), The Definitive ANTLR 4 Reference” is a good reference to get started with.

Ballerina grammar can be found under [`<ballerina>/compiler/ballerina-lang/src/main/resources/grammar/`](https://github.com/ballerina-platform/ballerina-lang/tree/master/compiler/ballerina-lang/src/main/resources/grammar) in the Ballerina repository. It consists of two files:

* BallerinaLexer.g4 - Contains the lexer rules for Ballerina grammar. Lexer is responsible for tokenizing an input Ballerina source code.
* BallerinaParser.g4 - Contains the parser rules. Parser listens to the token stream generated by the lexer. High level grammar productions/abstractions are defined in the parser using those tokens.

> TIP: If you want to check and validate a grammar rule you just wrote, you can use the ANTLR 4 plugin of your IDE. See Setting up your development environment section for more details.

#### Generating Parsers

Once a change is done to any of the grammar files, the lexer and the parser need to be re-generated. To generate the lexer and the parser, first download the `antlr-complete-4.5.3.jar` file. Then navigate to `<ballerina>/compiler/ballerina-lang/src/main/resources/grammar/`, and execute the following command:
```
java -jar <path-to-antlr-jar>/antlr-4.5.3-complete.jar *.g4 -package org.wso2.ballerinalang.compiler.parser.antlr4 -o ../../java/org/wso2/ballerinalang/compiler/parser/antlr4/
```

The above command will autogenerate some Java classes. Out of the autogenerated classes, `BallerinaParserBaseListener.java` in particular is important, as the Ballerina AST builder is written on top of it. Thus, if any new rules are added to the BallerinaParser.g4, above command will generate new methods in the `BallerinaParserBaseListener.java`, and you need to override those newly added methods inside `BLangParserListener.java` accordingly.

### Ballerina Compiler 
https://medium.com/@sameera.jayasoma/ballerina-compiler-design-3406acc2476c

### Creating a Patch for Review

#### Fork the Repo
#### Make Your Changes
#### Add Unit Tests

Build process automatically runs all the tests.

#### Commit to Your Fork

We follow these commit message requirements:

* Separate subject from body with a blank line
* Limit the subject line to 50 characters
* Capitalize the subject line
* Do not end the subject line with a period
* Use the imperative mood in the subject line
* Wrap the body at 72 characters
* Use the body to explain what and why vs. how

Please find details at: [https://chris.beams.io/posts/git-commit/](https://chris.beams.io/posts/git-commit/)

## Proposing Changes To Ballerina

Start with the discussion in the Ballerina-Dev Google Group.

Once there is enough consensus around the proposal, you will likely be asked to file an Issue in GitHub and label it as Proposal, to continue the discussion on details there.


