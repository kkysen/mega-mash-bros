package com.github.kkysen.supersmashbros.core;

import java.util.EnumMap;
import java.util.Map.Entry;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pools;
import com.github.kkysen.libgdx.util.KeyBinding;
import com.github.kkysen.libgdx.util.Renderable;
import com.github.kkysen.supersmashbros.actions.Action;

/**
 * 
 * 
 * @author Khyber Sen
 */

//TODO move hitboxes into attack. calculations that use dmg form hitbox use it from attack
public abstract class Player implements Renderable {
    
    private static final float WINNING_POINTS = 100; // FIXME
    
    private static final float FORCE_MULTIPLIER = 1; // FIXME
    private static final float POINTS_MULTIPLIER = 1; // FIXME
    
    private float percentage = 0;
    //private float weight;
    
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
    
    private final float points = 0;
    
    protected Player(final String name, final int id) {
        this.name = name;
        this.id = id;
        for (final Action action : getActions()) {
            actions.put(action.keyBinding, action);
        }
    }
    
    protected abstract Action[] getActions();
    
    public boolean isAlive(final Rectangle bounds) {
        return bounds.contains(state.position);
    }
    
    public boolean hasWon() {
        return points >= WINNING_POINTS;
    }
    
    //Points based on damage dealt (???)
    /*public void attacked(final float damage) {
        points += damage * POINTS_MULTIPLIER;
        percentage += damage
    }*/
    
    public void attacked(final Action action, final float damage) {
        percentage += damage;
        impulse(damage, action.getAngle(), action.getKnockBack());
    }
    
    public void checkHitPlatform(final Platform platform) {
        final Rectangle bounds = platform.rectangle;
        final Vector2 position = state.position;
        if (bounds.contains(position)) {
            position.y = bounds.y + bounds.height;
            velocity.y = 0;
            acceleration.y = 0;
            acceleration.x *= platform.friction;
        }
    }
    
    private void impulse(final float damage, final float angle, final float knockback) {
        acceleration.setAngle(angle);
        acceleration.set(1, 1);
        
        //the actual formula for this is long, we can tweak this as needed
        //I just made this up
        acceleration.scl(knockback + percentage * damage * 0.5f);
        
        final float deltaTime = Gdx.graphics.getDeltaTime();
        velocity.mulAdd(acceleration, deltaTime);
        state.position.mulAdd(velocity, deltaTime);
    }
    
    private void checkForHits(final Array<Player> enemies) {
        for (final Hurtbox hurtbox : hurtboxes) {
            for (final Player enemy : enemies) {
                
                // what used to be here
                /*for (Hitbox hitbox : enemy.hitboxes) {
                    float damage = hurtbox.collide(hitbox);
                    float angle = 0; // FIXME
                    impulse(damage, angle);
                }*/
                
                for (final Action action : enemy.actions.values()) {
                    //Assuming one move for now
                    //Would normally have to choose which hitbox is most relevant
                    
                    if (action.getHitboxes().size > 0) {
                        final float damage = hurtbox.damageTakenBy(action,
                                action.getHitboxes().first());
                        attacked(action, damage);
                    }
                    
                }
            }
        }
    }
    
    private void checkForActions() {
        for (final Entry<KeyBinding, Action> attackEntry : actions.entrySet()) {
            if (attackEntry.getKey().isPressed()) {
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
                Pools.free(boxes.removeIndex(i--));
            }
        }
    }
    
    public void update(final Array<Player> enemies) {
        updateBoxes(hitboxes);
        updateBoxes(hurtboxes);
        checkForHits(enemies);
        checkForActions();
    }
    
    public void kill() {
        // TODO
    }
    
    @Override
    public void render(final Batch batch) {
        state.render(batch);
    }
    
}
