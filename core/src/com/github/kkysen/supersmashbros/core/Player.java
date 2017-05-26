package com.github.kkysen.supersmashbros.core;

import java.util.EnumMap;
import java.util.Map;
import java.util.Map.Entry;

import com.badlogic.gdx.graphics.g2d.Batch;
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
    
    private final EnumMap<KeyBinding, Action> actions;
    private final Map<Action, State<?>> states;
    
    private final String name;
    private final int id;
    
    private State<?> state;
    
    private float health;
    
    public boolean isAlive() {
        return health > 0;
    }
    
    public Array<Hitbox> hitboxes() {
        return state.hitboxes;
    }
    
    private Array<Hurtbox> hurtboxes() {
        return state.hurtboxes;
    }
    
    private void checkForHits(final Array<Player> enemies) {
        for (final Hurtbox hurtbox : hurtboxes()) {
            for (final Player enemy : enemies) {
                for (final Hitbox hitbox : enemy.hitboxes()) {
                    health -= hurtbox.damageTakenBy(hitbox);
                }
            }
        }
    }
    
    private void checkForActions() {
        for (final Entry<KeyBinding, Action> attackEntry : actions.entrySet()) {
            if (attackEntry.getKey().isPressed()) {
                state = attackEntry.getValue().execute(state);
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
