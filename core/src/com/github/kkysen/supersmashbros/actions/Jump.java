package com.github.kkysen.supersmashbros.actions;

import com.badlogic.gdx.utils.Array;
import com.github.kkysen.libgdx.util.keys.KeyBinding;
import com.github.kkysen.supersmashbros.core.Hitbox;
import com.github.kkysen.supersmashbros.core.State;

/**
 * 
 * 
 * @author Khyber Sen
 */
public class Jump extends Action {
    
    /**
     * @param state
     * @param binding
     * @param hitboxes
     * @param startup
     * @param duration
     * @param cooldown
     * @param baseDamage
     * @param angle
     * @param knockback
     */
    public Jump(final State state, final KeyBinding binding, final Array<Hitbox> hitboxes,
            final float startup, final float duration, final float cooldown, final float baseDamage,
            final float angle, final float knockback) {
        super(state, binding, hitboxes, startup, duration, cooldown, 0, 0, 0);
        // TODO Auto-generated constructor stub
    }
    
}
