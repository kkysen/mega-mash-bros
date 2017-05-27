package com.github.kkysen.supersmashbros.actions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.github.kkysen.libgdx.util.KeyBinding;
import com.github.kkysen.supersmashbros.core.Hitbox;
import com.github.kkysen.supersmashbros.core.List;
import com.github.kkysen.supersmashbros.core.State;

import lombok.RequiredArgsConstructor;

/**
 * 
 * 
 * @author Khyber Sen
 */
@RequiredArgsConstructor
public class Action {
    private final State state;
    public final KeyBinding keyBinding;
    private final float startup;
    private final float cooldown;
    
    private float elapsedTime;
    
    public State execute(final State state, final Vector2 acceleration,
            final Vector2 velocity) {
        if (elapsedTime < cooldown) {
            elapsedTime += Gdx.graphics.getDeltaTime();
            return state;
        }
        elapsedTime = 0;
        return state;
    }
    
}
