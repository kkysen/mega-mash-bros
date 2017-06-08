package com.github.kkysen.supersmashbros.actions;

import com.github.kkysen.libgdx.util.keys.KeyBinding;
import com.github.kkysen.supersmashbros.core.Player;
import com.github.kkysen.supersmashbros.core.State;

public abstract class GroundAttack extends Attack {

	public GroundAttack(State state, KeyBinding keyBinding, State[] impossiblePreStates, float startup, float duration,
			float cooldown, float damage, float angle, float knockback) {
		super(state, keyBinding, impossiblePreStates, startup, duration, cooldown, damage, angle, knockback);
	}

	@Override
    protected final void move(final Player player) {
    	if (player.wasOnPlatform) player.velocity.x = 0;
    }
}
