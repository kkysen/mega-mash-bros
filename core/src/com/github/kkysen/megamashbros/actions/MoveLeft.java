package com.github.kkysen.megamashbros.actions;

import com.github.kkysen.libgdx.util.keys.KeyBinding;
import com.github.kkysen.megamashbros.core.Player;
import com.github.kkysen.megamashbros.core.State;

/**
 * 
 * 
 * @author Khyber Sen
 */
public class MoveLeft extends Move {
    
    public MoveLeft(final State state, final float duration, final float maxSpeed) {
        super(state, KeyBinding.LEFT, new State[] {}, 0, duration, 0, maxSpeed);
    }
    
    @Override
    protected void move(final Player player) {
        player.velocity.x = Math.max(-maxSpeed, player.velocity.x - adjustSpeed(player));
        player.facingRight = false;
    }
    
}
