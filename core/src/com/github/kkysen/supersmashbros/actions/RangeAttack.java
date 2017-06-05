package com.github.kkysen.supersmashbros.actions;

import com.badlogic.gdx.math.MathUtils;
import com.github.kkysen.libgdx.util.keys.KeyBinding;
import com.github.kkysen.supersmashbros.core.Hitbox;
import com.github.kkysen.supersmashbros.core.State;

public class RangeAttack extends Attack {
    
    public RangeAttack(final State state, final float startup, final float duration,
            final float cooldown,
            final float damage, final float knockback) {
        super(state, KeyBinding.MAIN_ATTACK, new State[] {}, startup, duration, cooldown, damage,
                75, knockback);
    }
    
    @Override
    protected void attack(final State state) {
    	if (timesUsed == 0) {
    		final Hitbox hitbox = state.newHitbox(this, 50f, 50f);
            hitbox.velocity.set(MathUtils.randomBoolean() ? 300f : -300f, 0f);
            //System.out.println("\t" + name() + " created " + hitbox + ", " + hitbox.motion());
            state.addHitbox(hitbox);
            timesUsed++;
    	}
    }
    
}
