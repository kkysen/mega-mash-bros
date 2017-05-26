package com.github.kkysen.supersmashbros.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import lombok.RequiredArgsConstructor;

/**
 * 
 * 
 * @author Khyber Sen
 */
@RequiredArgsConstructor
public abstract class Action {
    
    private final float cooldown;
    
    private float elapsedTime;
    
    public State execute(final State state, final Vector2 acceleration,
            final Vector2 velocity) {
        if (elapsedTime < cooldown) {
            elapsedTime += Gdx.graphics.getDeltaTime();
            return state;
        }
        elapsedTime = 0;
        return newState();
    }
    
    protected abstract State newState();
    
}
