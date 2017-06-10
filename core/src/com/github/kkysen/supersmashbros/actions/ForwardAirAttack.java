package com.github.kkysen.supersmashbros.actions;

import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.github.kkysen.libgdx.util.keys.KeyBinding;
import com.github.kkysen.supersmashbros.core.Hitbox;
import com.github.kkysen.supersmashbros.core.State;

public class ForwardAirAttack extends AirAttack {
    
    public ForwardAirAttack(final State state, final float startup, final float duration,
            final float cooldown, final float damage, final float knockback) {
        super(state, KeyBinding.FORWARD_TILT, new State[] {}, startup, duration, cooldown, damage,
                270, knockback);
    }
    
    @Override
    protected void attack(final State state, final boolean facingRight) {
        Timer.schedule(new Task() {
            
            final State temp = state;
            
            @Override
            public void run() {
                final Hitbox hitbox = temp.newHitbox(ForwardAirAttack.this, 50f, 50f);
                hitbox.angle = angle;
                hitbox.position.x += facingRight ? 20f : -20f;
                state.addHitbox(hitbox);
            }
            
        }, startup);
    }
    
}
