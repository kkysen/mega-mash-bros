package com.github.kkysen.megamashbros.actions;

import java.util.function.Function;

import com.github.kkysen.libgdx.util.keys.KeyBinding;
import com.github.kkysen.megamashbros.core.Player;
import com.github.kkysen.megamashbros.core.State;

/**
 * 
 * 
 * @author Khyber Sen
 */
public abstract class Executable implements Function<Player, State> {
    
    public final KeyBinding keyBinding;
    
    protected Executable(final KeyBinding keyBinding) {
        this.keyBinding = keyBinding;
    }
    
    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
    
    public abstract State execute(Player player);
    
    @Override
    public State apply(final Player player) {
        return execute(player);
    }
    
    public void update() {}
    
    public void reset() {}
    
}
