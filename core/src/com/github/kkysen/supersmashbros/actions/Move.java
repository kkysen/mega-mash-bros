package com.github.kkysen.supersmashbros.actions;

import com.badlogic.gdx.math.Vector2;
import com.github.kkysen.libgdx.util.keys.KeyBinding;
import com.github.kkysen.supersmashbros.core.State;

/**
 * 
 * 
 * @author Khyber Sen
 */
public abstract class Move extends Action {
    
    protected final float speed;
    
    public Move(final State state, final KeyBinding keyBinding, final float duration,
            final float cooldown, final float speed) {
        super(state, keyBinding, duration, cooldown);
        this.speed = speed;
    }
    
    @Override
    protected final void modifyState(final State state, final Vector2 position) {}
    
    @Override
    protected abstract void move(final Vector2 velocity);
    
}
