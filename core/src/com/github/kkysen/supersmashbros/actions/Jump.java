package com.github.kkysen.supersmashbros.actions;

import com.github.kkysen.libgdx.util.KeyBinding;
import com.github.kkysen.supersmashbros.core.State;

/**
 * 
 * 
 * @author Khyber Sen
 */
public class Jump extends Action {
    
    public Jump(final State state) {
        super(state, KeyBinding.JUMP, 2);
    }

    //Jumping doesn't deal damage
	@Override
	public float getBaseDmg() {
		return 0;
	}
    
}
