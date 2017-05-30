package com.github.kkysen.supersmashbros.actions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.github.kkysen.libgdx.util.keys.KeyBinding;
import com.github.kkysen.supersmashbros.core.Hitbox;
import com.github.kkysen.supersmashbros.core.State;

/**
 * 
 * 
 * @author Khyber Sen
 */
//An attack has hitboxes
public abstract class Attack extends Action {
    
    private final boolean inCooldown = false;
    
    /**
     * @param state
     * @param binding
     * @param hitboxes
     * @param startup
     * @param duration
     * @param cooldown
     * @param baseDamage
     * @param angle
     * @param knockback
     */
    public Attack(final State state, final KeyBinding binding, final Array<Hitbox> hitboxes,
            final float startup, final float duration, final float cooldown, final float baseDamage,
            final float angle, final float knockback) {
        super(state, binding, hitboxes, startup, duration, cooldown, baseDamage, angle, knockback);
        // TODO Auto-generated constructor stub
    }
    
    @Override
    public State execute(final State state, final Vector2 acceleration,
            final Vector2 velocity) {
        
        if (elapsedTime < startup) {
            elapsedTime += Gdx.graphics.getDeltaTime();
            return state;
        } else if (elapsedTime < duration) {
            //Perform attack
        } else if (elapsedTime < cooldown) {
            elapsedTime += Gdx.graphics.getDeltaTime();
            return state;
        }
        
        return state;
    }
    
}
