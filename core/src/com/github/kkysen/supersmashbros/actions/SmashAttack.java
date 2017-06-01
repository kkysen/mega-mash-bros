package com.github.kkysen.supersmashbros.actions;

import com.github.kkysen.libgdx.util.keys.KeyBinding;
import com.github.kkysen.supersmashbros.core.Hitbox;
import com.github.kkysen.supersmashbros.core.State;

public class SmashAttack extends Attack {
    
    public SmashAttack(final State state, final float startup, final float duration,
            final float cooldown,
            final float damage, final float knockback) {
        super(state, KeyBinding.MAIN_ATTACK, new State[] {}, startup, duration, cooldown, damage,
                180, knockback);
    }
    
    @Override
    protected void attack(final State state) {
        final Hitbox hitbox = state.newHitbox(this);
        hitbox.bounds.setSize(10f, 10f);
        hitbox.velocity.set(5f, 0f);
        state.addHitbox(hitbox);
    }
    
}
