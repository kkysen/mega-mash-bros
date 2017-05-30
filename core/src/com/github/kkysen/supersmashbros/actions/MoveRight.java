package com.github.kkysen.supersmashbros.actions;

import com.badlogic.gdx.math.Vector2;
import com.github.kkysen.libgdx.util.keys.KeyBinding;
import com.github.kkysen.supersmashbros.core.State;

/**
 * 
 * 
 * @author Khyber Sen
 */
public class MoveRight extends Move {
    
    public MoveRight(final State state, final float duration, final float speed) {
        super(state, KeyBinding.RIGHT, duration, 0, speed);
    }
    
    @Override
    protected void move(final Vector2 velocity) {
        velocity.x += speed;
    }
    
}
