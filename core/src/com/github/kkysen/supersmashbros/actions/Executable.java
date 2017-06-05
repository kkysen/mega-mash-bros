package com.github.kkysen.supersmashbros.actions;

import com.github.kkysen.libgdx.util.keys.KeyBinding;
import com.github.kkysen.supersmashbros.core.Player;
import com.github.kkysen.supersmashbros.core.State;

/**
 * 
 * 
 * @author Khyber Sen
 */
public abstract class Executable {
    
    public final KeyBinding keyBinding;
    
    protected Executable(final KeyBinding keyBinding) {
        this.keyBinding = keyBinding;
    }
    
    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
    
    public abstract State execute(Player player);
    
}
