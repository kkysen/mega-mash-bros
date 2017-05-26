package com.github.kkysen.supersmashbros.core;

import java.util.EnumMap;
import java.util.Map;
import java.util.Map.Entry;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.github.kkysen.libgdx.util.KeyBinding;
import com.github.kkysen.libgdx.util.Renderable;

import lombok.RequiredArgsConstructor;

/**
 * 
 * 
 * @author Khyber Sen
 */
@RequiredArgsConstructor
public class Player implements Renderable {
    
    private static final float FORCE_MULTIPLIER = 1;
    private static final float POINTS_MULTIPLIER = 1;
    
    private final EnumMap<KeyBinding, Action> actions;
    private final Map<Action, State<?>> states;
    
    private final String name;
    private final int id;
    
    private State<?> state;
    private final Vector2 acceleration = new Vector2();
    private final Vector2 velocity = new Vector2();
    
    private float points;
    
    public boolean isAlive(final Rectangle bounds) {
        return bounds.contains(state.position);
    }
    
    public Array<Hitbox> hitboxes() {
        return state.hitboxes;
    }
    
    private Array<Hurtbox> hurtboxes() {
        return state.hurtboxes;
    }
    
    public void attack(final float damage) {
        points += damage * POINTS_MULTIPLIER;
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
    
    private void impulse(final float damage, final float angle) {
        acceleration.setAngle(angle);
        acceleration.set(1, 1);
        acceleration.scl(damage * FORCE_MULTIPLIER);
        final float deltaTime = Gdx.graphics.getDeltaTime();
        velocity.mulAdd(acceleration, deltaTime);
        state.position.mulAdd(velocity, deltaTime);
    }
    
    private void checkForHits(final Array<Player> enemies) {
        for (final Hurtbox hurtbox : hurtboxes()) {
            for (final Player enemy : enemies) {
                for (final Hitbox hitbox : enemy.hitboxes()) {
                    final float damage = hurtbox.collide(hitbox);
                    final float angle = 0; // FIXME
                    // Stanley, I'm not sure how the angle should be calculated
                    impulse(damage, angle);
                }
            }
        }
    }
    
    private void checkForActions() {
        for (final Entry<KeyBinding, Action> attackEntry : actions.entrySet()) {
            if (attackEntry.getKey().isPressed()) {
                state = attackEntry.getValue().execute(state, acceleration, velocity);
            }
        }
    }
    
    public void update(final Array<Player> enemies) {
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
