package com.github.kkysen.supersmashbros.core;

import com.badlogic.gdx.utils.Array;
import com.github.kkysen.libgdx.util.keys.KeyInput;

/**
 * 
 * 
 * @author Khyber Sen
 */
public abstract class AIKeyInput extends KeyInput {
    
    protected abstract void makeDecisions(Player self, Array<Player> enemies);
    
}
