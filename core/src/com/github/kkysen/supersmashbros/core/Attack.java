package com.github.kkysen.supersmashbros.core;

import com.github.kkysen.supersmashbros.actions.Action;

/**
 * 
 * 
 * @author Khyber Sen
 */
public abstract class Attack extends Action {
    
    public Attack(final float cooldown) {
        super(cooldown);
    }
    
}
