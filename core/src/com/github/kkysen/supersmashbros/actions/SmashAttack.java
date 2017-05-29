package com.github.kkysen.supersmashbros.actions;

import com.badlogic.gdx.utils.Array;
import com.github.kkysen.libgdx.util.KeyBinding;
import com.github.kkysen.supersmashbros.core.Hitbox;
import com.github.kkysen.supersmashbros.core.State;

public class SmashAttack extends Attack {
    
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
    public SmashAttack(final State state, final KeyBinding binding, final Array<Hitbox> hitboxes,
            final float startup, final float duration, final float cooldown, final float baseDamage,
            final float angle, final float knockback) {
        super(state, binding, hitboxes, startup, duration, cooldown, baseDamage, angle, knockback);
        // TODO Auto-generated constructor stub
    }
    
}
