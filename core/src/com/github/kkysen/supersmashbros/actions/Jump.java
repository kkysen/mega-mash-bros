package com.github.kkysen.supersmashbros.actions;

import com.badlogic.gdx.math.Vector2;
import com.github.kkysen.libgdx.util.keys.KeyBinding;
import com.github.kkysen.supersmashbros.core.State;

/**
 * 
 * 
 * @author Khyber Sen
 */
public class Jump extends Move {
    
    public Jump(final State state, final float duration, final float cooldown, final float speed) {
        super(state, KeyBinding.JUMP, new State[] {}, 0, duration, cooldown, speed);
    }
    
    @Override
    protected void move(final Vector2 velocity, boolean isOnPlatform) {
        error("someone jumped");
        velocity.y += speed;
    }
    
}
