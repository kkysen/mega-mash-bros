package com.github.kkysen.supersmashbros.actions;

import com.github.kkysen.libgdx.util.keys.KeyBinding;
import com.github.kkysen.supersmashbros.core.Player;
import com.github.kkysen.supersmashbros.core.State;

/**
 * 
 * 
 * @author Khyber Sen
 */
public class Jump extends Move {
    
    //private int numMidairJumps = 1;
    public boolean jumpPressed = false;
    
    public Jump(final State state, final float duration, final float cooldown, final float speed) {
        super(state, KeyBinding.JUMP, new State[] {}, 0, duration, cooldown, speed);
    }
    
    @Override
    protected void move(final Player player) {
        //if (alreadyUsed) return;
        
        final boolean isOnPlatform = player.wasOnPlatform;
        if (isOnPlatform || player.numMidairJumps++ <= 1 && !jumpPressed) {
            error("someone jumped");
            System.out.println("someone jumped");
            player.velocity.y = maxSpeed;
            if (isOnPlatform) {
                player.numMidairJumps = 1;
            }
            jumpPressed = true;
        } /*
          else if (player.stunTime > 0|| numMidairJumps++ <= 1) {
          
          }*/
    }
    
    @Override
    public void reset() {
        super.reset();
        jumpPressed = false;
    }
}
