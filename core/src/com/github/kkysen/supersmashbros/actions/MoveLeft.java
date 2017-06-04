package com.github.kkysen.supersmashbros.actions;

import com.badlogic.gdx.math.Vector2;
import com.github.kkysen.libgdx.util.keys.KeyBinding;
import com.github.kkysen.supersmashbros.core.State;

/**
 * 
 * 
 * @author Khyber Sen
 */
public class MoveLeft extends Move {
	
    public MoveLeft(final State state, final float duration, final float speed) {
        super(state, KeyBinding.LEFT, new State[] {}, 0, duration, 0, speed);
    }
    
    @Override
    protected void move(final Vector2 velocity, final boolean isOnPlatform) {
        velocity.x = Math.max(-speed, velocity.x - speed);
    }
    
}
