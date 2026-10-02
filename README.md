# Flappy Bird

A small Flappy Bird style desktop game written in Java. Guide the bird through pipe gaps, avoid collisions, and try to get the highest score.

## Features

- Side-scrolling pipes with randomly positioned gaps
- Gravity-based bird movement and keyboard flapping
- Pipe and lower-boundary collision detection
- Score tracking and restart after game over

## Gameplay

The bird falls under gravity while pairs of pipes move from right to left. Press Space to flap upward and pass through each gap. A collision with a pipe or falling below the play area ends the round.

## Controls

| Key | Action |
| --- | --- |
| Space | Flap; after game over, restart the game |

## Screenshots

Screenshots could not be captured in this environment because it has no interactive desktop display available for a reliable capture of the game window.

## Requirements

- JDK 8 or newer

The project uses standard Java and Swing APIs and has no external library dependencies.

## How to Run

From the project root, compile the source files and run `App`:

```sh
javac -d bin App.java FlappyBird.java
java -cp "bin;." App
```

The classpath includes the project root so the game can load its PNG resources. On macOS or Linux, use `:` instead of `;` in the `java -cp` argument:

```sh
java -cp "bin:." App
```

## Project Structure

| File | Purpose |
| --- | --- |
| `App.java` | Creates and displays the Swing window. |
| `FlappyBird.java` | Implements drawing, movement, pipe placement, collisions, scoring, and keyboard input. |
| `flappybird.png` | Bird sprite. |
| `flappybirdbg.png` | Background image. |
| `toppipe.png` | Upper pipe sprite. |
| `bottompipe.png` | Lower pipe sprite. |
| `.vscode/` | VS Code Java project settings. |
| `lib/` | Reserved for dependencies; currently empty. |

## Technologies

- Java
- Swing
- AWT for drawing and keyboard input

## Game Mechanics

Gravity increases the bird's downward velocity each frame. Space sets its vertical velocity upward. Pipes spawn in pairs every 1.5 seconds with a randomized vertical position and a fixed gap, then scroll left. Passing each pipe contributes half a point, giving one point per pair. The game ends when the bird intersects a pipe or falls below the board. Pressing Space after game over resets the bird, pipes, and score.

## Future Improvements

- Add a title screen and a pause option.
- Add sound effects and persistent high scores.
- Remove pipes after they leave the play area.

## Author

**yoson**

- GitHub: [yo5on](https://github.com/yo5on)
- Repository: [Flappy-bird](https://github.com/yo5on/Flappy-bird)
