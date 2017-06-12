# Mega Mash Bros
This game intends to be a simple clone of Super Smash Bros.
Super Smash Bros is a type of platform fighting game where the objective is not depleting the opponent's health, but rather knocking them off the stage/platform.

### Features
* Hitboxes and hurtboxes (this is the most difficult and primary focus)
* A unified game experience, meaning all these other features work in harmony
* Characters playable by both the user and an AI
* Various moves, aerial and ground
* Platform movement
* A simple AI (not the main focus)

### Description
Rather than in traditional fighting games, where players attempt to whittle down their opponent's health gradually in a closed arena, Super Smash Bros. attempts to take a different approach.  Attacks dealt will increase the accumulated damage the opponent holds (otherwise known as percentage), with the ultimate goal of knocking the opponent out of bounds, which will become easier when the opponent is at higher percentages.

We are trying to mimic the mechanics of the actual game.  Every attack will have a different launch angle and enemy knockback will be determined based on their their damage and the type of attack used.

### Usage
Run java -jar MegaMashBros.jar in the root of the project folder (or double click on it). To control the type/number of players, edit core/assets/options.json.  If there were more than just Mario right now, you could change the character of each player, yourself and the AIs.  Right now, however, you can control the controller (AI type) and number of each AI by editing the corresponding json fields.

### Controls
Key | Action
----|-------
A | Move left
D | Move right
W | Jump
I | Up tilt/air
K | Down tilt/air
L | Side tilt/air
SPACE | Ranged attack
L CTRL + SPACE | Pause the game
R CTRL + SPACE | Resume the game
R | Restart the game

### Game mechanics overview
Every player has one mid-air jump, which can be used to recover and jump back onto the stage after falling below it or to reach high up opponents. If a player falls or flies off of any of the screen boundaries, they lose.

There are 3 main attacks: tilts, aerials, and ranged attacks. "Tilts" refer to ground attacks and "aerials" refer to attacks performed in the air. Every attack has both starting lag and ending lag. This means that you will be unable to move for a short period of time and will be vulnerable to attack. Experiment with the various moves to find attacks that can be used to combo the opponent!

* Up tilt - An upward fist punch that lifts the enemy into the air a bit
* Side tilt - A strong kick that can really send opponents flying
* Down tilt - A leg sweep that can deal with enemies running towards you
* Ranged attack - Shoots a projectile forward. Has significant lag in the air
* Up air - An overhead kick that sends opponents flying sky-high
* Side air - A slow, laggy punch. If it connects, it will send your opponents straight down. Try using this off the side of the stage!
* Down air - A quick spin that knocks away opponents

There are boxes showing the hurtboxes and hitboxes around and created by the players.  Yellow is the user's hurtbox, turquoise is the user's hitboxes, blue is the enemy's hurtbox, and red is the enemy's hitboxes.  These were kept to make it easier to see what is being targeted and where the attacks are actually occuring.  Eventually these would be removed, but since the animation isn't perfect yet, we kept them in to help the user see exactly what was happening.

### Development Stages
- [x] 5/24: Plan out project
- [x] 5/28: Implement basic physics
- [x] 5/29: Finish convoluted class relationships
- [x] 5/29: Implement controller input, user and simple AI
- [x] 5/29: Implement hitboxes and hurtboxes
- [x] 5/29: Implement characters
- [x] 5/29: Implement actions, moves, and attacks
- [x] 6/02: Work on graphics and animation
- [x] 6/09: Add more actions and moves
- [x] 6/10: Refine actions, their animations, and their state changes
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
* Player mechanics

### Documented Bugs
* After attacking or getting hit, when you are stunned and were previously running, sometimes you keep sliding on the platform without being able to stop.
* If the AI bot gets very close to you in a certain position, it may stop trying to attack you.
* There is an extremely rare NullPointerException that sometimes happens when a delayed air attack is cancelled after landing, and is likely due to a multithreading race condition.
* When there are many AIs all on the screen, they act very erratic, because they are constantly switching between other AIs for who they are trying to target.
* The JumpingAI doesn't continue jumping, but since it wasn't very important, it was never fixed.

### Future Goals
* Add more characters, moves, and animations, which isn't too hard since the core functionality is working, but just wasn't the main focus.
* Add a start screen to the game where you can choose character and opponents, instead of just having an options.json file that is read when the game starts.
* Displaying current stats on the screen, like how much damage was dealt or how much percentage each player has, for example.
* Make the game more resizable (i.e. changing the screen size) and make the player sizes scale up (not that easy to do).
* Improve the AI (it was very good at one point, but as the game mechanics changed, it got worse and was never fully fixed).
* Adding more interesting worlds with better background graphics.

