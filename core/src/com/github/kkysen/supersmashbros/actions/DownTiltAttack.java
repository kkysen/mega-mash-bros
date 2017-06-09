package com.github.kkysen.supersmashbros.actions;

import com.github.kkysen.libgdx.util.keys.KeyBinding;
import com.github.kkysen.supersmashbros.core.Hitbox;
import com.github.kkysen.supersmashbros.core.Player;
import com.github.kkysen.supersmashbros.core.State;

public class DownTiltAttack extends GroundAttack {

	public DownTiltAttack(State state, float startup, float duration, float cooldown,
			float damage, float knockback) {
		super(state, KeyBinding.DOWN_TILT, new State[]{}, startup, duration, cooldown,
				damage, 88, knockback);
	}

	@Override
	protected void attack(State state, boolean facingRight) {
		final Hitbox hitbox = state.newHitbox(this, 50f, 50f);
        hitbox.angle = (float)((facingRight) ? angle : Math.PI-angle);
        if (facingRight) {
        	hitbox.position.add(20f, -20f);
        }
        else {
        	hitbox.position.add(-20f, -20f);
        }
        state.addHitbox(hitbox);
		
	}
	
}
