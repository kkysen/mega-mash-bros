package com.github.kkysen.megamashbros.actions;

import com.github.kkysen.libgdx.util.keys.KeyBinding;
import com.github.kkysen.megamashbros.core.Player;
import com.github.kkysen.megamashbros.core.State;

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
    protected void move(final Player player) {
    	if (player.wasOnPlatform) {
    		player.velocity.x = 0;
            player.acceleration.x = 0;
    	}
        
    }
    
}
