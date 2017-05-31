#World
The World class is the main class encompassing the whole game.  It contains a background Texture and a single rectangular Platform.  It also has an Array<Player> for all the players, although I assume it will normally just be two players.  When the World is rendered, it loops through all the players, updating them, checking if anyone won the game yet, checking if anyone died by falling off, checking if they hit the platform, and then rendering the players themselves.

#Player
The Player class contains a name and id (unused right now), a Map<KeyBinding, Action> for all the possible actions it may do in response to pressed keys, a State that holds the rendered, animated state of the Player, and all the hitboxes and hurtboxes produced by the Player.

The Player also keeps track of the velocity and acceleration to figure out where the Player should be rendered, but the position vector itself is stored inside the State, because that's where the Player is actually rendered.

When the Player is updated, a bunch of things happens.  First all of its hitboxes and hurtboxes are updated.  If the duration of an attack that produced a hitbox, for example, has expired, then that hitbox is destroyed.

The Player also keeps track of its points/percentage (I think I just called it something else), so that it knows when it has won the game.

Then the Player checks for hits by enemy hitboxes.  It loops through its own hurtboxes, and then for each hurtbox it loops through all the enemies, and then for each enemy it loops through all the enemy's hitboxes.  For each hitbox, it finds the "damage" done by the collision of the hurtbox and hitbox proportional to the overlapping area.  Somehow it will also calculate an angle for the attack.  In void impulse(float damaga, float angle), the Player's position, velocity, and acceleration are all updated according to the damage done by the collision (we still have to continuously update these and decrease the acceleration later).

Then the Player checks for any actions the user might have requested by pressing the corresponding keys.  It loops through the KeyBindings in the actions map, and for any KeyBinding that is pressed, it executes that Action, updating the State (or replacing it) and the position, etc. in the process.  Then it also adds/removes any hitboxes or hurtboxes produced by this Action's new State.

#Action
For the Action itself, I envisioned it having a new State it will produce when executed, a KeyBinding it requires to be called, and a startup/cooldown period.  It wasn't supposed to contain its own hitboxes or hurtboxes; those were supposed to be added to the Player through the Action's State's methods.  But since it controls what state it produces, the Action can still change the hitboxes and hurtboxes of the Player.

#State
The State contains the Animation<Texture> (or potentially Animation<TextureRegion> or something else) used to actually render the animation of the Player.  It also contains the Player's position so that it knows where to render the Player.

Since the State contains the Animation and controls the added and removed hitboxes and hurtboxes when an Action is executed, it is very specific to one Player, containing Textures, Sprites, etc. specific to that Player, as well as hitboxes and hurtboxes specific to that Player's size and other attributes.

On the other hand, the Action constructor accepts a State, KeyBinding, and startup/cooldown, so besides the State, it can be more generic with respect to which Player created it.  So I envision there being more generic/abstract Action classes for different moves and attacks and then to create the final, concrete Action class, a Player-specific State is added to it.

We might be able to abstract the hitbox and hurtbox creation more, so that could possibly be moved up into the Action class, and maybe the State only refines the boxes created, making them more Player-specific.