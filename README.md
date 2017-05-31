# Super Smash Bros Clone
This game intends to be a simple clone of Super Smash Bros.
Super Smash Bros is a type of platform fighting game where the objective is not depleting the opponent's health, but rather knocking them off the stage/platform.

### Features
* Attack simulations based on attacking hitboxes and opponent hurtboxes (this is the most difficult and primary focus)
* A unified game experience, meaning all these other features work in harmony
* Playable characters, playable by both a user or an AI
* Various moves for each player
* A simple AI (not the main focus)

### Description
Rather than in traditional fighting games, where players attempt to whittle down their opponent's health gradually in a closed arena, Super Smash Bros. attempts to take a different approach.  Attacks dealt will increase the accumulated damage the opponent holds (otherwise known as percentage), with the ultimate goal of knocking the opponent out of bounds, which will become easier when the opponent is at higher percentages.

We are trying to mimic the mechanics of the actual game.  Every attack will have a different launch angle and enemy knockback will be determined based on their their damage and the type of attack used.

### Development Stages
- [x] 5/24: Plan out project
- [x] 5/28: Implement basic physics
- [x] 5/29: Finish convoluted class relationships
- [x] 5/29: Implement controller input, user and simple AI
- [x] 5/29: Implement hitboxes and hurtboxes
- [x] 5/29: Implement characters
- [x] 5/29: Implement actions, moves, and attacks
- [ ] 6/02: Begin work on graphics and animation
- [ ] 6/11: Debug

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
