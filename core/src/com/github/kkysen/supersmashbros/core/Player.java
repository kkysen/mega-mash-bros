package com.github.kkysen.supersmashbros.core;

import java.util.EnumMap;
import java.util.Map;
import java.util.Map.Entry;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pools;
import com.github.kkysen.libgdx.util.ExtensionMethods;
import com.github.kkysen.libgdx.util.KeyBinding;
import com.github.kkysen.libgdx.util.Loggable;
import com.github.kkysen.libgdx.util.Renderable;
import com.github.kkysen.supersmashbros.actions.Action;

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
 * rendered, but the {@link State#position} vector itself is stored inside the
 * {@link #state}, because that's where the {@link Player} is actually rendered.
 * <br>
 * Then the {@link Player} checks for hits by enemy {@link #hitboxes}. It loops
 * through its own {@link #hurtboxes}, and then for each {@link Hurtbox}, it
 * loops through all the enemies, and then for each enemy it loops through all
 * the enemy's {@link #hitboxes}. For each {@link Hitbox}, it finds the
 * "{@link Hitbox#damage}" done by the collision of the {@link Hurtbox} and
 * {@link Hitbox} proportional to the overlapping area. Somehow it will also
 * calculate an {@link Action#angle} for the attack. In
 * {@link #knockback(float, float)}, the {@link Player}'s
 * {@link State#position}, {@link #velocity}, and {@link #acceleration} are all
 * updated according to the damage done by the collision (we still have to
 * continuously update these and decrease the {@link #acceleration} later).
 * <br>
 * Then the {@link Player} checks for any {@link #actions} the user might have
 * requested by pressing the corresponding keys. It loops through the
 * {@link KeyBinding}s in the {@link #actions} map, and for any
 * {@link KeyBinding} that is pressed, it executes that {@link Action}, updating
 * the {@link #state} (or replacing it) and the {@link State#position}, etc. in
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
    
    private final EnumMap<KeyBinding, Action> actions = new EnumMap<>(KeyBinding.class);
    
    private final String name;
    private final int id;
    
    private State state;
    
    /**
     * Hitboxes retrieved from attacks, empty when not attacking
     */
    private final Array<Hitbox> hitboxes = new Array<>();
    private final Array<Hurtbox> hurtboxes = new Array<>();
    
    private final Vector2 acceleration = new Vector2();
    private final Vector2 velocity = new Vector2();
    
    // I think these two, points and percentage, are the same
    
    private float points = 0;
    
    private float percentage = 0;
    //private float weight;
    
    protected Player(final String name, final int id) {
        this.name = name;
        this.id = id;
        
        // FIXME, not sure how this should work
        for (final Action action : getActions()) {
            actions.put(action.keyBinding, action);
        }
    }
    
    // FIXME, see right above
    protected abstract Action[] getActions();
    
    @Override
    public String toString() {
        return "Player " + id + " " + name;
    }
    
    /**
     * Determines if this {@link Player} is still alive, which it will be as
     * long as it has remained within the bounds of the {@link World}.
     * 
     * @param bounds the rectangular bounds of the {@link World} in which this
     *            {@link Player} must stay.
     * @return true if this {@link Player} is within @param bounds and thus is
     *         still alive
     */
    public final boolean isAlive(final Rectangle bounds) {
        return bounds.contains(state.position);
    }
    
    /**
     * Determines if this {@link Player} has won the game based off of how many
     * points (may be changed to equivalent percentage) it has gained, which are
     * gained by attacking other {@link Player}'s.
     * 
     * @return true if this {@link Player} has won the game
     */
    public final boolean hasWon() {
        return points >= WINNING_POINTS;
    }
    
    //Points based on damage dealt (???)
    /*public void attacked(final float damage) {
        points += damage * POINTS_MULTIPLIER;
        percentage += damage
    }*/
    
    /**
     * Allows this {@link Player} to update its own stats when one of its
     * {@link Hitbox}es successfully attacked another {@link Player}'s
     * {@link Hurtbox}.
     * 
     * @param damage the amount of damage this Player inflicted in its attack
     */
    public void attack(final float damage) {
        log("attacked, inflicting " + damage + " damage");
        points += damage * POINTS_MULTIPLIER;
    }
    
    public void attacked(final Action action, final float damage) {
        percentage += damage;
        impulse(damage, action.angle, action.knockback);
    }
    
    public final void checkHitPlatform(final Platform platform) {
        final Rectangle bounds = platform.bounds;
        final Vector2 position = state.position;
        if (bounds.contains(position)) {
            log(this + " hit platform and stopped");
            position.y = bounds.maxY();
            velocity.set(0, 0);
            acceleration.set(0, 0);
        }
    }
    
    // old implementation
    // What's the difference b/w damage and knockback?
    // Isn't knockback based on the damage done?
    private void knockback(final float damage, final float angle) {
        log(this + " knockedback by " + damage * FORCE_MULTIPLIER /* * massReciprocal*/ + " at "
                + angle + "°");
        // do Players have mass/inertia?;
        acceleration.setAngleAndLength(angle, damage * FORCE_MULTIPLIER /* * massReciprocal*/);
        move();
    }
    
    private void impulse(final float damage, final float angle, final float knockback) {
        //the actual formula for this is long, we can tweak this as needed
        //I just made this up
        acceleration.setAngleAndLength(angle, knockback + percentage * damage * 0.5f);
        acceleration.accelerate(velocity, state.position);
    }
    
    private void checkForHits(final Array<Player> enemies) {
        for (final Hurtbox hurtbox : hurtboxes) {
            for (final Player enemy : enemies) {
                log(this + " checking for hits by " + enemy);
                // what used to be here
                for (final Hitbox hitbox : enemy.hitboxes) {
                    final float damage = hurtbox.collide(hitbox);
                    final float angle = 0; // FIXME
                    log(this + " attacked by " + hitbox + ", inflicting " + damage + " damage at "
                            + angle + "°");
                    knockback(damage, angle);
                }
                
                for (final Action action : enemy.actions.values()) {
                    //Assuming one move for now
                    //Would normally have to choose which hitbox is most relevant
                    
                    if (action.hitboxes.size > 0) {
                        final float damage = hurtbox.damageTakenBy(action,
                                action.hitboxes.first());
                        attacked(action, damage);
                    }
                    
                }
            }
        }
    }
    
    private void checkForActions() {
        log(this + " checking for called actions");
        for (final Entry<KeyBinding, Action> attackEntry : actions.entrySet()) {
            if (attackEntry.getKey().isPressed()) {
                log(this + " pressed " + attackEntry.getKey());
                log(this + " " + attackEntry.getValue() + "ing");
                state = attackEntry.getValue().execute(state, acceleration, velocity);
                state.addHitboxes(hitboxes);
                state.addHurtboxes(hurtboxes);
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
    
    private void move() {
        log(this + " moving");
        acceleration.accelerate(velocity, state.position);
    }
    
    public final void update(final Array<Player> enemies) {
        log(this + " updating hitboxes");
        updateBoxes(hitboxes);
        log(this + " updating hurtboxes");
        updateBoxes(hurtboxes);
        checkForHits(enemies);
        checkForActions();
        move();
    }
    
    public final void kill() {
        // TODO
    }
    
    @Override
    public final void render(final Batch batch) {
        state.render(batch);
    }
    
}
