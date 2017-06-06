package com.github.kkysen.supersmashbros.actions;

import com.github.kkysen.libgdx.util.keys.KeyBinding;
import com.github.kkysen.supersmashbros.core.Player;
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
    protected void move(final Player player) {
        player.velocity.x = Math.max(-speed, player.velocity.x - speed);
        player.facingRight = false;
    }
    
}
