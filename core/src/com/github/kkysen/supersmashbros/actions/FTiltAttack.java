package com.github.kkysen.supersmashbros.actions;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.github.kkysen.libgdx.util.keys.KeyBinding;
import com.github.kkysen.supersmashbros.core.Hitbox;
import com.github.kkysen.supersmashbros.core.State;

public class FTiltAttack extends Attack {
	
	public FTiltAttack(final State state, final float startup, final float duration,
            final float cooldown,
            final float damage, final float knockback) {
        super(state, KeyBinding.F_TILT, new State[] {}, startup, duration, cooldown, damage,
                75, knockback);
    }

	@Override
	protected void attack(State state) {
		if (timesUsed == 0) {
			Vector2 temp = state.player.position.cpy();
			temp.add(20, 20);
			final Hitbox hitbox = state.newHitbox(this, 50f, 50f, temp);
			//hitbox.velocity.set(MathUtils.randomBoolean() ? 300f : -300f, 0f);
	        System.out.println("\tF_Tilt " + hitbox);
	        state.addHitbox(hitbox);
	        timesUsed++;
		}
	}
}
