package com.github.kkysen.supersmashbros.core;

import java.util.Map;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
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
import com.github.kkysen.supersmashbros.actions.Executable;
import com.github.kkysen.supersmashbros.actions.ForwardTiltAttack;
import com.github.kkysen.supersmashbros.actions.Move;
import com.github.kkysen.supersmashbros.ai.AI;
import com.github.kkysen.supersmashbros.app.Game;

import lombok.experimental.ExtensionMethod;

/**
 * The {@link Player} class contains a {@link #name} and {@link #id} (unused
 * right now), a {@link Map}&lt;{@link KeyBinding}, {@link Action}&gt; for all
 * the possible {@link #executables} it may do in response to pressed keys, a
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
 * Then the {@link Player} checks for any {@link #executables} the user might
 * have
 * requested by pressing the corresponding keys. It loops through the
 * {@link KeyBinding}s in the {@link #executables} map, and for any
 * {@link KeyBinding} that is pressed, it executes that {@link Action}, updating
 * the {@link #state} (or replacing it) and the {@link #position}, etc. in
 * the process. Then it also adds/removes any {@link #hitboxes} or
 * {@link #hurtboxes} produced by this {@link Action}'s new {@link State}.
 * 
 * @author Khyber Sen
 */
@ExtensionMethod(ExtensionMethods.class)
public abstract class Player implements Renderable, Loggable {
    
    private static final float KNOCKBACK_MULTIPLIER = 0.1f;
    private static final float PERCENTAGE_MULTIPLIER = 0.001f;
    private static final float HITSTUN_MULTIPLIER = 0.00001f;
    
    public static int numPlayers = 0;
    
    public World world;
    
    public final Controller controller;
    private final Executable[] executables;
    
    private final String name;
    public final int id;
    public int lives;
    
    public State state;
    
    /**
     * Hitboxes retrieved from attacks, empty when not attacking
     */
    public final Array<Hitbox> hitboxes = new Array<>();
    public final Array<Hurtbox> hurtboxes = new Array<>();
    
    public final Vector2 acceleration = new Vector2();
    public final Vector2 velocity = new Vector2();
    public final Vector2 position = new Vector2();
    
    private float percentage = 0;
    
    public boolean wasOnPlatform = true;
    
    public boolean facingRight = true;
    
    private float stunTime = 0;
    
    protected Player(final String name, final Controller controller, final State initialState,
            final int lives, final Executable[] executables) {
        this.name = name;
        id = numPlayers++;
        this.controller = controller;
        state = initialState.clone();
        state.setPlayer(this);
        System.out.println(state);
        this.lives = lives;
        // EnumMap was throwing some weird errors because of some Eclipse compiler error,
        // so I just made my own "EnumMap"
        this.executables = executables;
        hurtboxes.add(new Hurtbox(this));
    }
    
    // these should be replaced by something better, but will do for now
    
    @Deprecated
    public float normalWidth() {
        return 50f; // FIXME
    }
    
    @Deprecated
    public float normalHeight() {
        return 50f; // FIXME
    }
    
    @Override
    public String toString() {
        return "Player " + id + " " + controller.name() + " " + name();
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
    
    public final boolean isAI() {
        return controller instanceof AI;
    }
    
    /**
     * Knock backs a player at a certain angle and knockback value, increasing
     * {@link #percentage} in the process.
     * 
     * @param damage damage done to this {@link Player}
     * @param angle angle in radians at which this {@link Player} was attacked
     * @param knockback the hard-coded {@link Hitbox#knockback} value
     */
    private void knockback(final float damage, final float angle, final float knockback) {
        final float accelerationMagnitude = knockback * damage * (percentage + 1)
                * /* * massReciprocal*/ KNOCKBACK_MULTIPLIER;
        System.out.println(this + " knocked back by " + accelerationMagnitude + " at "
                + MathUtils.radiansToDegrees * angle + "°, increasing percentage to " + percentage
                + "%");
        percentage += damage * PERCENTAGE_MULTIPLIER;
        acceleration.setAngleAndLength(angle, accelerationMagnitude);
        stunTime += accelerationMagnitude * HITSTUN_MULTIPLIER;
        System.out.println(this + " stunned for " + stunTime + " sec");
        move();
    }
    
    private void takeHits(final Array<Player> enemies) {
        for (final Hurtbox hurtbox : hurtboxes) {
            for (final Player enemy : enemies) {
                log(this + " checking for hits by " + enemy);
                for (final Hitbox hitbox : enemy.hitboxes) {
                    final float damage = hurtbox.collide(hitbox);
                    if (damage == 0) {
                        continue;
                    }
                    final Attack attack = hitbox.attack;
                    System.out.println(this + " attacked by " + hitbox + ", inflicting " + damage
                            + " damage and "
                            + attack.knockback + " knockback at "
                            + MathUtils.radiansToDegrees * attack.angle + "°");
                    knockback(damage, attack.angle, attack.knockback);
                }
            }
        }
    }
    
    private void updateBoxes(final Array<? extends Box> boxes) {
        for (int i = 0; i < boxes.size; i++) {
            if (!boxes.get(i).update()) {
                log(boxes.get(i) + " deleted");
                Pools.free(boxes.removeIndex(i--));
            }
        }
    }
    
    public final boolean isOnPlatform() {
        return world.platform.bounds.contains(position);
    }
    
    private void stop() {
        state = executables[KeyBinding.STOP.ordinal()].execute(this);
    }
    
    private void checkIfOnPlatform() {
        final boolean isOnPlatform = isOnPlatform();
        if (isOnPlatform) {
            log(this + " hit platform and stopped");
            position.y = world.platform.bounds.maxY();
            velocity.y = 0;
            acceleration.y = 0;
            if (!wasOnPlatform) {
                stop();
            }
        } else {
            acceleration.y = world.gravity;
        }
        wasOnPlatform = isOnPlatform;
    }
    
    private void move() {
        //error(this + " moving at " + velocity + ", position = " + position);
        acceleration.accelerate(velocity, position);
    }
    
    private void executeExecutables() {
        if (stunTime > 0) {
            stunTime -= Game.deltaTime;
            return;
        }
        stunTime = 0;
        log(this + " checking for called executables");
        //System.out.println(controller);
        boolean noMovesCalled = true;
        for (int i = 0; i < executables.length; i++) {
            final Executable executable = executables[i];
            if (executable instanceof Action) {
                ((Action) executable).update();
            }
            if (executable.keyBinding.isPressed(controller)) {
                //System.out.println(this + " pressed " + KeyBinding.get(i));
                //System.out.println(this + " tried calling " + action);
                state = executable.execute(this);
                if (executable instanceof Move) {
                    noMovesCalled = false;
                }
            } else if (executable instanceof Attack) {
                ((Attack) executable).reset();
            }
        }
        if (noMovesCalled && wasOnPlatform && !(state.action instanceof ForwardTiltAttack)) {
            stop();
        }
    }
    
    public final void update(final Array<Player> enemies) {
        controller.update();
        log(this + " updating hitboxes");
        updateBoxes(hitboxes);
        log(this + " updating hurtboxes");
        updateBoxes(hurtboxes);
        takeHits(enemies);
        checkIfOnPlatform();
        executeExecutables();
        move();
    }
    
    public final void kill() {
        error(this + " was killed");
        hitboxes.clear();
        hurtboxes.clear();
        executables.clear();
        // TODO
    }
    
    @Override
    public final void render(final Batch batch) {
        state.render(batch);
    }
    
    @Override
    public final void render(final ShapeRenderer lineRenderer) {
        for (final Hitbox hitbox : hitboxes) {
            hitbox.render(lineRenderer);
        }
        for (final Hurtbox hurtbox : hurtboxes) {
            hurtbox.render(lineRenderer);
        }
    }
    
}
