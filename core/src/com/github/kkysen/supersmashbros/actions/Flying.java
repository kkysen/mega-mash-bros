package com.github.kkysen.supersmashbros.actions;

import com.badlogic.gdx.math.Vector2;
import com.github.kkysen.libgdx.util.keys.KeyBinding;
import com.github.kkysen.supersmashbros.core.State;

/**
 * When a player is hit, they cannot move for the duration of their flight
 * */
public class Flying extends Action {

	public Flying(State state, KeyBinding keyBinding, State[] impossiblePreStates, float warmupTime, float duration,
			float cooldown) {
		super(state, keyBinding, impossiblePreStates, warmupTime, duration, cooldown);
	}
	
	
	@Override
	protected void attack(final State state) {}
    
	@Override
    protected void move(final Vector2 acceleration, final Vector2 velocity,
            final boolean isOnPlatform) {
	}
}
