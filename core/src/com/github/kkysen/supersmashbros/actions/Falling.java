package com.github.kkysen.supersmashbros.actions;

import com.badlogic.gdx.math.Vector2;
import com.github.kkysen.libgdx.util.keys.KeyBinding;
import com.github.kkysen.supersmashbros.core.State;

public class Falling extends Move {

	public Falling(State state, KeyBinding keyBinding, State[] impossiblePreStates, float startup, float duration,
			float cooldown, float speed) {
		super(state, keyBinding, impossiblePreStates, startup, duration, cooldown, speed);
	}

	@Override
	protected void move(Vector2 acceleration, Vector2 velocity, boolean isOnPlatform) {
		acceleration.x = 0;
	}

}
