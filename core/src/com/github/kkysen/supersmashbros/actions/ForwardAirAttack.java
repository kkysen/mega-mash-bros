package com.github.kkysen.supersmashbros.actions;

import com.badlogic.gdx.utils.Timer;
import com.github.kkysen.libgdx.util.keys.KeyBinding;
import com.github.kkysen.supersmashbros.core.Hitbox;
import com.github.kkysen.supersmashbros.core.Player;
import com.github.kkysen.supersmashbros.core.State;

public class ForwardAirAttack extends AirAttack {

	public ForwardAirAttack(State state, float startup, float duration,
			float cooldown, float damage, float knockback) {
		super(state, KeyBinding.FORWARD_TILT, new State[]{}, startup, duration, cooldown, damage, 270, knockback);
	}

	@Override
	protected void attack(State state, boolean facingRight) {
		final State temp = state;
		Timer.instance().scheduleTask(new Timer.Task() {

			@Override
			public void run() {
				final Hitbox hitbox = temp.newHitbox(ForwardAirAttack.this, 50f, 50f);
		        hitbox.angle = angle;
		        if (facingRight) {
		        	hitbox.position.add(20f, 0f);
		        }
		        else {
		        	hitbox.position.add(-20f, 0f);
		        }
		        state.addHitbox(hitbox);
			}
    		
    	}, startup);
		
		
//		final Hitbox hitbox = state.newHitbox(this, 50f, 50f);
//        hitbox.angle = angle;
//        if (facingRight) {
//        	hitbox.position.add(20f, 0f);
//        }
//        else {
//        	hitbox.position.add(-20f, 0f);
//        }
//        state.addHitbox(hitbox);
	}

}
