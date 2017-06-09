package com.github.kkysen.supersmashbros.ai;

import com.badlogic.gdx.utils.Array;
import com.github.kkysen.libgdx.util.keys.KeyBinding;
import com.github.kkysen.supersmashbros.core.Player;

public class JumpAI extends AI {

	@Override
	public void makeDecisions(Player self, Array<Player> enemies) {
		pressKeys(KeyBinding.JUMP);
	}

}
