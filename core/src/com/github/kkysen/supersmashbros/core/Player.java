package com.github.kkysen.supersmashbros.core;

import java.util.Map;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pools;
import com.github.kkysen.libgdx.util.ExtensionMethods;
import com.github.kkysen.libgdx.util.Loggable;
import com.github.kkysen.libgdx.util.Renderable;
import com.github.kkysen.libgdx.util.keys.Controller;
import com.github.kkysen.libgdx.util.keys.KeyBinding;
import com.github.kkysen.supersmashbros.actions.Action;
import com.github.kkysen.supersmashbros.actions.Attack;
import com.github.kkysen.supersmashbros.ai.AI;

import lombok.experimental.ExtensionMethod;

/**
 * The {@link Player} class contains a {@link #name} and {@link #id} (unused
 * right now), a {@link Map}&lt;{@link KeyBinding}, {@link Action}&gt; for all
 * the possible {@link #actions} it may do in response to pressed keys, a
 * {@link State} that holds the rendered, animated {@link #state} of the
 * {@link Player}, and all the {@link #hitboxes} and {@link #hurtboxes} produced
 * by the {@link Player}.
 * <br>
 * The {@link Player} also keeps track of the {@link #velocity} and
 * {@link #acceleration} to figure out where the {@link Player} should be
 * rendered, but the {@link #position} vector itself is stored inside the
 * {@link #state}, because that's where the {@link Player} is actually rendered.
 * <br>
 * Then the {@link Player} checks for hits by enemy {@link #hitboxes}. It loops
 * through its own {@link #hurtboxes}, and then for each {@link Hurtbox}, it
 * loops through all the enemies, and then for each enemy it loops through all
 * the enemy's {@link #hitboxes}. For each {@link Hitbox}, it finds the
 * "{@link Hitbox#damage}" done by the collision of the {@link Hurtbox} and
 * {@link Hitbox} proportional to the overlapping area. Somehow it will also
 * calculate an {@link Attack#angle} for the attack. In
 * {@link #knockback(float, float)}, the {@link Player}'s
 * {@link #position}, {@link #velocity}, and {@link #acceleration} are all
 * updated according to the damage done by the collision (we still have to
 * continuously update these and decrease the {@link #acceleration} later).
 * <br>
 * Then the {@link Player} checks for any {@link #actions} the user might have
 * requested by pressing the corresponding keys. It loops through the
 * {@link KeyBinding}s in the {@link #actions} map, and for any
 * {@link KeyBinding} that is pressed, it executes that {@link Action}, updating
 * the {@link #state} (or replacing it) and the {@link #position}, etc. in
 * the process. Then it also adds/removes any {@link #hitboxes} or
 * {@link #hurtboxes} produced by this {@link Action}'s new {@link State}.
 * 
 * @author Khyber Sen
 */
@ExtensionMethod(ExtensionMethods.class)
public abstract class Player implements Renderable, Loggable {
    
    // TODO move hitboxes into attack. calculations that use dmg form hitbox use it from attack
    
    private static final float WINNING_POINTS = 100; // FIXME
    
    private static final float FORCE_MULTIPLIER = 1; // FIXME
    private static final float POINTS_MULTIPLIER = 1; // FIXME
    
    private static int numPlayers = 0;
    
    public World world;
    
    public final Controller controller;
    private final Action[] actions = new Action[KeyBinding.values().length];
    
    private final String name;
    private final int id;
    public final int lives;
    
    public State state;
    
    /**
     * Hitboxes retrieved from attacks, empty when not attacking
     */
    protected final Array<Hitbox> hitboxes = new Array<>();
    protected final Array<Hurtbox> hurtboxes = new Array<>();
    
    protected final Vector2 acceleration = new Vector2();
    public final Vector2 velocity = new Vector2();
    public final Vector2 position = new Vector2();
    
    // I think these two, points and percentage, are the same
    
    private float points = 0;
    
    private float percentage = 0;
    //private float weight;
    
    protected Player(final String name, final Controller controller, final State initialState,
            final int lives, final Action[] actions) {
        this.name = name;
        id = ++numPlayers;
        this.controller = controller;
        state = initialState;
        this.lives = lives;
        initialState.setPlayer(this);
        // EnumMap was throwing some weird errors because of some Eclipse compiler error,
        // so I just made my own "EnumMap"
        for (final Action action : actions) {
            this.actions[action.keyBinding.ordinal()] = action;
        }
    }
    
    @Override
    public String toString() {
        return "Player " + id + " " + name;
    }
    
    public final void reSpawn(final Batch batch) {
        // TODO
    }
    
    /**
     * Determines if this {@link Player} is still alive, which it will be as
     * long as it has remained within the {@link World#bounds} of the
     * {@link World}.
     * 
     * @return true if this {@link Player} is within the {@link World#bounds}
     *         and thus is still alive
     */
    public final boolean isAlive() {
        // I think this should just be based on one life,
        // because I assume something happends when you die,
        // like you respawn somewhere else
        // I added the method below to check if someone was totally dead
        return world.bounds.contains(position) /*&& lives > 0*/;
    }
    
    public final boolean isCompletelyDead() {
        return lives <= 0;
    }
    
    /**
     * Determines if this {@link Player} has won the game based off of how many
     * {@link #points} (may be changed to equivalent {@link #percentage}) it has
     * gained, which are
     * gained by attacking other {@link Player}s.
     * 
     * @return true if this {@link Player} has won the game
     */
    /*public final boolean hasWon() {
        return points >= WINNING_POINTS;
    }*/
    
    public final boolean isAI() {
        return controller instanceof AI;
    }
    
    //Points based on damage dealt (???)
    /*public void attacked(final float damage) {
        points += damage * POINTS_MULTIPLIER;
        percentage += damage
    }*/
    
    /**
     * Allows this {@link Player} to update its own stats when one of its
     * {@link #hitboxes} successfully attacked another {@link Player}'s
     * {@link Hurtbox}.
     * 
     * @param damage the amount of damage this Player inflicted in its attack
     */
    // not sure if we need this anymore, I was confused about points/percentage
    @Deprecated
    public void attack(final float damage) {
        log("attacked, inflicting " + damage + " damage");
        points += damage * POINTS_MULTIPLIER;
    }
    
    @Deprecated
    public void attacked(final Attack attack, final float damage) {
        percentage += damage;
        impulse(damage, attack.angle, attack.knockback);
    }
    
    /**
     * What's the difference b/w damage and knockback?
     * Isn't knockback based on the damage done?
     * 
     * @param damage damage done to this {@link Player}
     * @param angle angle in radians at which this {@link Player} was attacked
     */
    private void knockback(final float damage, final float angle) {
        log(this + " knockedback by " + damage * FORCE_MULTIPLIER /* * massReciprocal*/ + " at "
                + angle + "°");
        // do Players have mass/inertia?;
        acceleration.setAngleAndLength(angle, damage * FORCE_MULTIPLIER /* * massReciprocal*/);
        move();
    }
    
    @Deprecated
    private void impulse(final float damage, final float angle, final float knockback) {
        // the actual formula for this is long, we can tweak this as needed
        // I (Stanley) just made this up
        acceleration.setAngleAndLength(angle, knockback + percentage * damage * 0.5f);
        acceleration.accelerate(velocity, position);
    }
    
    private void takeHits(final Array<Player> enemies) {
        for (final Hurtbox hurtbox : hurtboxes) {
            for (final Player enemy : enemies) {
                log(this + " checking for hits by " + enemy);
                for (final Hitbox hitbox : enemy.hitboxes) {
                    final float damage = hurtbox.collide(hitbox);
                    final float angle = hitbox.angle; // FIXME
                    // or should it be...
                    final float angle2 = hitbox.velocity.angleRad(); // FIXME
                    log(this + " attacked by " + hitbox + ", inflicting " + damage + " damage at "
                            + angle + "°");
                    knockback(damage, angle);
                    // FIXME percentage should increase here somehow
                    // I was confused about percentage before,
                    // I think I mostly get it now
                    percentage += damage /* * something*/;
                }
                
                //                for (final Action action : enemy.actions.values()) {
                //                    //Assuming one move for now
                //                    //Would normally have to choose which hitbox is most relevant
                //
                //                    if (action.hitboxes.size > 0) {
                //                        final float damage = hurtbox.damageTakenBy(action,
                //                                action.hitboxes.first());
                //                        attacked(action, damage);
                //                    }
                //
                //                }
            }
        }
    }
    
    private void executeActions() {
        log(this + " checking for called actions");
        for (int i = 0; i < actions.length; i++) {
            final Action action = actions[i];
            action.update();
            if (KeyBinding.get(i).isPressed(controller)) {
                error(this + " pressed " + KeyBinding.get(i));
                error(this + " tried calling " + action);
                state = action.execute(this);
            }
        }
    }
    
    private void updateBoxes(final Array<? extends Box> boxes) {
        final int size = boxes.size;
        for (int i = 0; i < size; i++) {
            if (!boxes.get(i).update()) {
                log(boxes.get(i) + " deleted");
                Pools.free(boxes.removeIndex(i--));
            }
        }
    }
    
    private void checkIfOnPlatform() {
        final Rectangle bounds = world.platform.bounds;
        if (bounds.contains(position)) {
            log(this + " hit platform and stopped");
            position.y = bounds.maxY();
            velocity.y = 0;
            velocity.x = 0;
            acceleration.y = 0;
        } else {
            acceleration.y = world.gravity;
        }
    }
    
    private void move() {
        log(this + " moving at " + velocity);
        acceleration.accelerate(velocity, position);
    }
    
    public final void update(final Array<Player> enemies) {
        log(this + " updating hitboxes");
        updateBoxes(hitboxes);
        log(this + " updating hurtboxes");
        updateBoxes(hurtboxes);
        takeHits(enemies);
        checkIfOnPlatform();
        executeActions();
        move();
    }
    
    public final void kill() {
        error(this + " was killed");
        // TODO
    }
    
    @Override
    public final void render(final Batch batch) {
        state.render(batch);
    }
    
}
