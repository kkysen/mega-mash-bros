package com.github.kkysen.supersmashbros.actions;

import com.badlogic.gdx.utils.Timer;
import com.github.kkysen.libgdx.util.keys.KeyBinding;
import com.github.kkysen.supersmashbros.core.Hitbox;
import com.github.kkysen.supersmashbros.core.State;

public class UpTiltAttack extends GroundAttack {

	public UpTiltAttack(State state, float startup, float duration,
			float cooldown, float damage, float knockback) {
		super(state, KeyBinding.UP_TILT, new State[]{}, startup, duration, cooldown, damage, 88, knockback);
	}

	@Override
	protected void attack(State state, boolean facingRight) {
		
        
        final State temp = state;
		Timer.instance().scheduleTask(new Timer.Task() {

			@Override
			public void run() {
				final Hitbox hitbox = state.newHitbox(UpTiltAttack.this, 20f, 40f);
		        hitbox.angle = (float)((facingRight) ? angle : Math.PI-angle);
		        if (facingRight) {
		        	hitbox.position.add(20f, 20f);
		        }
		        else {
		        	hitbox.position.add(-5f, 20f);
		        }
		        state.addHitbox(hitbox);
			}
    		
    	}, startup);
	}

}
