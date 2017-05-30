package com.github.kkysen.supersmashbros.players;

import com.github.kkysen.libgdx.util.keys.KeyInput;
import com.github.kkysen.supersmashbros.actions.Action;
import com.github.kkysen.supersmashbros.core.Player;

/**
 * 
 * 
 * @author Khyber Sen
 */
public class Mario extends Player {
    
    protected Mario(final String name, final int id, final KeyInput input, final Action[] actions) {
        super(name, id, input, actions);
    }
    
}
