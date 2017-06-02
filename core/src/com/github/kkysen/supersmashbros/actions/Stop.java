package com.github.kkysen.supersmashbros.actions;

import com.badlogic.gdx.math.Vector2;
import com.github.kkysen.libgdx.util.keys.KeyBinding;
import com.github.kkysen.supersmashbros.core.State;

/**
 * 
 * 
 * @author Khyber Sen
 */
public class Stop extends Move {
    
    public Stop(final State state) {
        super(state, KeyBinding.STOP, new State[] {}, 0, 0, 0, 0);
    }
    
    @Override
    protected void move(final Vector2 velocity, final boolean isOnPlatform) {
        velocity.x = 0;
    }
    
}
