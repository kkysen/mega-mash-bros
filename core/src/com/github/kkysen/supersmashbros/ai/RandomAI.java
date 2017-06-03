package com.github.kkysen.supersmashbros.ai;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.github.kkysen.libgdx.util.keys.KeyBinding;
import com.github.kkysen.supersmashbros.core.Player;

/**
 * 
 * 
 * @author Khyber Sen
 */
public class RandomAI extends AI {
    
    @Override
    public void makeDecisions(final Player self, final Array<Player> enemies) {
        if (MathUtils.randomBoolean(0.01f)) {
            pressKeys(KeyBinding.random());
        }
    }
    
}