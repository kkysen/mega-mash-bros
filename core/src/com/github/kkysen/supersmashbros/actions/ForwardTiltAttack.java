package com.github.kkysen.supersmashbros.actions;

import com.github.kkysen.libgdx.util.keys.KeyBinding;
import com.github.kkysen.supersmashbros.core.Hitbox;
import com.github.kkysen.supersmashbros.core.Player;
import com.github.kkysen.supersmashbros.core.State;

/**
 * 
 * 
 * @author Khyber Sen
 */
public class ForwardTiltAttack extends GroundAttack {
    
    public ForwardTiltAttack(final State state, final float startup, final float duration,
            final float cooldown, final float damage, final float knockback) {
        super(state, KeyBinding.FORWARD_TILT, new State[] {}, startup, duration, cooldown, damage,
                75, knockback);
    }
    
    @Override
    protected void attack(final State state, final boolean facingRight) {
        final Hitbox hitbox = state.newHitbox(this, 50f, 30f);
        hitbox.angle = (float)((facingRight) ? angle : Math.PI-angle);
        if (facingRight) {
        	hitbox.position.add(20f, 7f);
        }
        else {
        	hitbox.position.add(-20f, 7f);
        }
        state.addHitbox(hitbox);
    }
    
}
