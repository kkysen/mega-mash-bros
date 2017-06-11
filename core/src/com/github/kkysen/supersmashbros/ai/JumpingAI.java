package com.github.kkysen.supersmashbros.ai;

import com.badlogic.gdx.utils.Array;
import com.github.kkysen.libgdx.util.keys.KeyBinding;
import com.github.kkysen.supersmashbros.core.Player;

public class JumpingAI extends AI {
    
    @Override
    public void makeDecisions(final Player self, final Array<Player> enemies) {
        if ((cycle & cycles - 1) != 0) {
            return; // only run every AI#cycles game loops
        }
        pressKeys(KeyBinding.JUMP);
    }
    
}
