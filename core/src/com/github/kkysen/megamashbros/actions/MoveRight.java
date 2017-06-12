package com.github.kkysen.megamashbros.actions;

import com.github.kkysen.libgdx.util.keys.KeyBinding;
import com.github.kkysen.megamashbros.core.Player;
import com.github.kkysen.megamashbros.core.State;

/**
 * 
 * 
 * @author Khyber Sen
 */
public class MoveRight extends Move {
    
    public MoveRight(final State state, final float duration, final float maxSpeed) {
        super(state, KeyBinding.RIGHT, new State[] {}, 0, duration, 0, maxSpeed);
    }
    
    @Override
    protected void move(final Player player) {
        player.velocity.x = Math.min(maxSpeed, player.velocity.x + adjustSpeed(player));
        player.facingRight = true;
    }
    
}
