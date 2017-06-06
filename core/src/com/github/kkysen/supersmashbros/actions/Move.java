package com.github.kkysen.supersmashbros.actions;

import com.github.kkysen.libgdx.util.keys.KeyBinding;
import com.github.kkysen.supersmashbros.core.Player;
import com.github.kkysen.supersmashbros.core.State;

/**
 * 
 * 
 * @author Khyber Sen
 */
public abstract class Move extends Action {
    
    protected final float speed;
    
    public Move(final State state, final KeyBinding keyBinding, final State[] impossiblePreStates,
            final float startup, final float duration, final float cooldown, final float speed) {
        super(state, keyBinding, impossiblePreStates, startup, duration, cooldown);
        this.speed = speed;
    }
    
    @Override
    protected abstract void move(Player player);
    
}
