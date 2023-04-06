# Duality
## 1. About
Duality is a twin stick shooter video game. In this game you control a shape which can shoot down enemies.
The unique mechanic of Duality is that there are two "Polarities", which are represented by different colors.
When a player hits an enemy of the same color (or vice versa), no damage is applied.
This requires the player to constantly switch colors in order to defeat the most enemies.

### 1.1. Demo
Add a link to a demo of your project.

## 2. Authors
This project was created by:
* David Pittracher 
* Fabio Vitalba

## 3. Usage
Describe how to compile, package, and run your project here.

To build the project, run:

```shell
mvn package
```

To do X, run:

```
mvn abc
```

## 4. Implementation
### 4.1. Architectural Overview
Describe the architecture of your application.


Here's a Class Diagram of our most important classes: [Game Class Diagram](https://github.com/Programming-Project-2021-22/course-project-davidfabio/blob/main/GAME-CLASS-DIAGRAM.md)
Here's a Flowchart of our UI: [UI Flowchart](https://github.com/Programming-Project-2021-22/course-project-davidfabio/blob/main/UI-FLOWCHART.md)

### 4.2. Third-Party Libraries
These are the Third-Party Libraries we've used:
- **LibGDX**: This library provides us with some basic functionalities needed to create a game. We were able to use features like: Shape-Renderers, Cameras, Sound-players and more.
- **Shade UI**: Skin for UI (Buttons, Progress Bars, and so on). It is freely available under [czyzby's GDX Skins GitHub Repository](https://github.com/czyzby/gdx-skins).

### 4.3. Programming Techniques
List and explain how you used the 10 programming techniques required for this project.
- **Graphical User Interface**: We used the built-in Libraries of LibGDX in order to create various User Interfaces for Game Menus, Settings, Highscores and User Interface in Game. 
- **Method Overriding**: Since we're using inheritance and interfaces we predefine a few methods at a high level (Example `Entity` provides `init()`, `update()`, `render()` methods.). However, some Entities require special behaviour and therefore need to override the original implementation of the method. For example the `Player` class requires a different `update()` method than the `EnemyChaser` class.
- **Interfaces**: `Movable`, `Attackable`, `Attacker` Interfaces _**...to be explained...**_

### 4.4. Tests
Briefly describe and motivate your test suite.

## 5. Experience
### 5.1. Overall Experience
Describe your overall experience in developing this project.

### 5.2. Division of Responsibilities
Describe the roles and responsibilities each member had in this project.
- **David Pittracher:** ...
- **Fabio Vitalba:** ...

### 5.3. Main Challenges
Elaborate on the main challenges each group member faced throughout the project and how they were surpassed.
- **David Pittracher:** ...
- **Fabio Vitalba:** Migrating from gradle (LibGDX's prefered Bundler) to Maven was quite painful. Coordinating the work was difficult at first, but once we figured out how to use the GitHub Issues it was a lot better.

### 5.4. Learning Outcomes
Describe what you learned with this project.
- **David Pittracher:** ...
- **Fabio Vitalba:** I learned a new library called `LibGDX` and David helped me learn something more regarding Game Development.
