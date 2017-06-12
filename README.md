# Mega Mash Bros
This game intends to be a simple clone of Super Smash Bros.
Super Smash Bros is a type of platform fighting game where the objective is not depleting the opponent's health, but rather knocking them off the stage/platform.

### Features
* Hitboxes and hurtboxes (this is the most difficult and primary focus)
* A unified game experience, meaning all these other features work in harmony
* Characters playable by both the user and an AI
* Various moves, aerial and ground
* A simple AI (not the main focus)

### Description
Rather than in traditional fighting games, where players attempt to whittle down their opponent's health gradually in a closed arena, Super Smash Bros. attempts to take a different approach.  Attacks dealt will increase the accumulated damage the opponent holds (otherwise known as percentage), with the ultimate goal of knocking the opponent out of bounds, which will become easier when the opponent is at higher percentages.

We are trying to mimic the mechanics of the actual game.  Every attack will have a different launch angle and enemy knockback will be determined based on their their damage and the type of attack used.

### Usage
Run the jar in the root of the project folder. To control the type/number of AI controlled players, go to core/assets and open the options.json file. Edit the amount of each AI player that you want.

### Game mechanics overview
Every player has one midair jump, of which can be used to recover back on the stage after falling below it or to reach high up opponents. If a player hits any of the screen boundaries, they lose.

There are 3 main attacks: tilts, aerials, and ranged attacks. "Tilts" refer to ground attacks and "aerials" refer to attacks performed in the air. Every attack has both starting lag and ending lag. This means that you will be unable to move for a short period of time and will be vulnerable to attack. Experiment with the various moves to find attacks that can be used to combo the opponent!

Up tilt - An upward fist punch that lifts the enemy into the air a bit
Side tilt - A strong kick that can really send opponents flying
Down tilt - A leg sweep that can deal with enemies running towards you
Ranged attack - Shoots a projectile forward. Has significant lag in the air
Up air - An overhead kick that sends opponents flying sky-high
Side air - A slow, laggy punch. If it connects, it will send your opponents straight down. Try using this off the side of the stage!
Down air - A quick spin that knocks away opponents


### Controls
A - Move left
D - Move right
W - Jump

I - Up tilt/air
K - Down tilt/air
L - Side tilt/air
SPACE - Ranged attack

### Development Stages
- [x] 5/24: Plan out project
- [x] 5/28: Implement basic physics
- [x] 5/29: Finish convoluted class relationships
- [x] 5/29: Implement controller input, user and simple AI
- [x] 5/29: Implement hitboxes and hurtboxes
- [x] 5/29: Implement characters
- [x] 5/29: Implement actions, moves, and attacks
- [x] 6/02: Begin work on graphics and animation
- [x] 6/11: Debug

### Stretch Goals
* More characters
* More moves
* Better AI
* Refined user experience

### Work Distribution
##### Stanley
* Animation
* Game mechanics
* Hitbox/hurtbox handling
* Player programming

##### Khyber
* Class relationships
* User and AI input
* Physics
* Game setup
* Player programming

