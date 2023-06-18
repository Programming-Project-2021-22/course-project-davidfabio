# Polygon Wars
## Table of Contents

- [Description](#1-description)
  - [Demo](#11-demo)
  - [Controls](#12-controls)
- [Authors](#2-authors)
- [Usage](#3-usage)
  - [Build](#build)
  - [Execution](#execution)
  - [Documentation](#documentation)
- [Implementation](#4-implementation)
  - [Architectural Overview](#41-architectural-overview)
  - [Third-Party Libraries](#42-third-party-libraries)
  - [Programming Techniques](#43-programming-techniques)
  - [Tests](#44-tests)
- [Experience](#5-experience)
  - [Overall Experience](#51-overall-experience)
  - [Division of Responsibilities](#52-division-of-responsibilities)
  - [Main Challenges](#53-main-challenges)
  - [Learning Outcomdes](#54-learning-outcomes)

## 1. Description
Polygon Wars is a simple shooter video game. Inspired by 80's arcade games, the game world looks simple and is easy to read.
In this game **you control a simple circle shape**, that can shoot, move and even dash. **Your goal is to survive** until all 
enemies are dead and no more enemies are spawned. Killing enemies grants points and pickups, collect them all to **reach
the highest score of anyone you know!**

### 1.1. Demo
**_Add a link to a demo of your project._**

### 1.2. Controls
- Movement
  - `W` `A` `S` `D` or `↑` `←` `↓` `→` to **move** on the Map
  - `Right-Mouse` or ` ` (space) to **dash** (keep pressed to aim)
- Combat
  - `Left-Mouse` to **shoot**
- Gameplay
  - `Esc` to **pause/resume**
  - `Tab` to **restart** (this is for Debugging only)

## 2. Authors
This project was created by:
* David Pittracher 
* Fabio Vitalba

## 3. Usage
_Unfortunately LibGDX only has unofficial maven support. So in order to run the game you will have to execute the steps below._
In order to run this Project you will need to have both `java` (**At least JAVA 17**) and [maven](https://maven.apache.org/download.cgi) installed on your machine.

Once you've installed both, you'll need to first [build](#build) then [execute](#execution) the project through command line.

#### Any of the following commands need to be executed at the projects root folder!

### Build
To build the project, run:
```shell
mvn package
```

You can also compile
```shell
mvn compile
```
or test the project
```shell
mvn test
```

### Execution
To play the game, run:
```shell
mvn exec:exec
```

Alternatively you can also run the jar-directly, in this case run the following:
#### On Windows & Linux:
```shell
java -jar ./target/polygonwars-1.0.0-jar-with-dependencies.jar
```

#### On MacOS:
```shell
java -jar -XstartOnFirstThread ./target/polygonwars-1.0.0-jar-with-dependencies.jar
```
> Note: The flag `-XstartOnFirstThread` is only required for MacOS, if you're on any other Operating system, you should omit this flag.

### Documentation
In order to generate the documentation for the project use the following command:
```shell
mvn javadoc:javadoc
```
You will find the generated documentation under `./target/site/jacoco/index.html`.

## 4. Implementation
### 4.1. Architectural Overview
Our Project is divided into three main Java Packages:
- org.davidfabio.**game**
  - Contains anything that represents the Game-World and Game-Flow.
- org.davidfabio.game.**enemies**
  - Contains all the enemy logics. Since these became very specific, we opted to move them to a seperate package.
- org.davidfabio.**input**
  - Contains anything that is required to handle User Inputs (Key-presses, Mouse movement, ...)
- org.davidfabio.**ui**
  - Contains anything that creates User Interfaces (Main Menu, In-game UI, ...)
- org.davidfabio.**utils**
  - Contains anything that is useful for all packages or is not strictly Game-World-specific.

We have some diagrams showing the relationship between our implemented classes. Please have a look at these diagrams before delving into our codebase.
- **UI Flowchart**
  - This flowchart explains very well how the Programm is started and which classes are called to reach the actual Game.
  - [UI Flowchart](https://github.com/Programming-Project-2021-22/course-project-davidfabio/blob/main/UI-FLOWCHART.md)
- **Game Class-Diagram**
  - This Class Diagram illustrates how the Game-World is composed. It does not contain all specific classes and subclasses, but it's a good start to understand what is present in a Game-World and how it's connected.
  - [Game Class Diagram](https://github.com/Programming-Project-2021-22/course-project-davidfabio/blob/main/GAME-CLASS-DIAGRAM.md)
- **World Class-Diagram**
  - This Class Diagram shows all the main classes like `player`, `enemy`, `entity`, `bullet` and all their derivatives.
  - [World Class Diagram](https://github.com/Programming-Project-2021-22/course-project-davidfabio/blob/main/WORLD-CLASS-DIAGRAM.md)

### 4.2. Third-Party Libraries
These are the Third-Party Libraries we've used:
- **LibGDX**: This library provides us with some basic functionalities needed to create a game. We were able to use features like: Shape-Renderers, Cameras, Sound-players and more.
- **Shade UI**: (This is no library, but a preexisting Resource we're using.) Skin for UI (Buttons, Progress Bars, and so on). It is freely available under [czyzby's GDX Skins GitHub Repository](https://github.com/czyzby/gdx-skins).
- **Jackson**: This library allows us to easily serialize and deserialize objects into JSON format for storage in a file.

### 4.3. Programming Techniques
List and explain how you used the 10 programming techniques required for this project.
- **Graphical User Interface**: We used the built-in Libraries of LibGDX in order to create various User Interfaces for Game Menus, Settings, Highscores and User Interface in Game. 
- **Method Overriding**: Since we're using inheritance and interfaces we predefine a few methods at a high level (Example `Entity` provides `init()`, `update()`, `render()` methods.). However, some Entities require special behaviour and therefore need to override the original implementation of the method. For example the `Player` class requires a different `update()` method than the `EnemyChaser` class.
- **Interfaces**: `Movable`, `Attackable`, `Attacker` Interfaces. These are used to unify the combat behaviour for Players, Enemies and other Entities.
- **Method Overloading**: Various methods use the overloading technique to provide easy-to-use methods. For example:
  - In the `UIBuilder` class we use method overloading to provide various functions to create UI-controls in default settings, or by overloading, with specific settings.
- **Collections**: In order to store various data we use collections. For example:
  - When storing past `Score`s in order to list the highscores.
  - When storing enemies in the `World`-class to periodically update their behaviour and spawn new ones.
- **Try-Catch-Blocks**: In order to safely load the sounds and music we rely on Try-Catch blocks to avoid any errors on missing files. We also use these Try-Catch blocks to avoid RunTimeErrors while writing or reading from files.
- **File I/O**: In order to store settings and scores we use files and therefore we need to write and read from files.
- **Serialization**: In order to store Settings and Scores in a sensible way, we serialize them into a JSON. We use Jackson for this.
- **Deserialization**: In order to read our serialized Settings and Scores we need to deserialize them from JSON. We use Jackson for this.
- **Test Hooks**: For our Test Suite we use `@BeforeAll` and `@AfterAll` in order to clean our Test environment. For example the `JSONFileManagementTest` uses these hooks to clean the Files created.
- **Streams**: In the High Scores screen we use streams to show the best 5 scores present in the Scores List.

### 4.4. Tests
Testing a LibGDX application has proven to be very tricky. This is because of two reasons mainly:
- Testing UI is always difficult.
- Many LibGDX-methods, which we use throughout our game, can only be run in the main thread and require some kind of initialization. Both requirements that we cannot satisfy through jUnit Tests. Therefore Classes like the `Camera` cannot be tested as they would require Gdx.graphics to be set up, which again requires an Application to run on the main thread.

Because of these constraints we tried to test as much of our non-LibGDX-related classes as possible. Obviously this still leaves a lot of room for potential errors.

## 5. Experience
### 5.1. Overall Experience
Overall, we had fun iterating over the various features and ideas of the Project. Both David and I learned a lot in the process. We also learned from eachother since we both have different backgrounds in programming and are used to different styles of programming.
We were very quick at building a working prototype and this helped us get a lot of the groundwork done early.

### 5.2. Division of Responsibilities
We had no clear division of responsibilities, but we ended up with the following responsibilities:
- **David Pittracher:** Game Design
- **Fabio Vitalba:** UI Design, Documentation, Project structure

We both used GitHub Issues in order to track the outstanding tasks and features.

### 5.3. Main Challenges
Elaborate on the main challenges each group member faced throughout the project and how they were surpassed.
- **David Pittracher:** ...
- **Fabio Vitalba:** Migrating from gradle (LibGDX's preferred Bundler) to Maven was quite painful. Coordinating the work was difficult at first, but once we figured out how to use the GitHub Issues it was a lot better.

### 5.4. Learning Outcomes
Describe what you learned with this project.
- **David Pittracher:** ...
- **Fabio Vitalba:** I learned a new library called `LibGDX` and David helped me learn something more regarding Game Development.
