package com.github.kkysen.supersmashbros.players;

import java.util.EnumMap;

import com.github.kkysen.libgdx.util.KeyBinding;
import com.github.kkysen.supersmashbros.core.Action;
import com.github.kkysen.supersmashbros.core.Player;

/**
 * 
 * 
 * @author Khyber Sen
 */
public class Mario extends Player {
    
    protected Mario(final String name, final int id) {
        super(name, id);
    }
    
    @Override
    protected void addActions(final EnumMap<KeyBinding, Action> actions) {
        
    }
    
}
