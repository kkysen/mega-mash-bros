package com.github.kkysen.supersmashbros.actions;

import com.badlogic.gdx.math.Vector2;
import com.github.kkysen.libgdx.util.keys.KeyBinding;
import com.github.kkysen.supersmashbros.core.State;

public class SmashAttack extends Attack {
    
    public SmashAttack(final State state, final float duration, final float cooldown,
            final float damage, final float knockback) {
        super(state, KeyBinding.MAIN_ATTACK, duration, cooldown, damage, 180, knockback);
    }
    
    @Override
    protected void modifyState(final State state, final Vector2 position) {
        state.addHitbox(state.newHitbox(duration, damage, position, 10f, 10f, 5f, 0f));
    }
    
}
