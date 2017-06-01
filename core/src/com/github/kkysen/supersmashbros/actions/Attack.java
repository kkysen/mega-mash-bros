package com.github.kkysen.supersmashbros.actions;

import com.badlogic.gdx.math.Vector2;
import com.github.kkysen.libgdx.util.keys.KeyBinding;
import com.github.kkysen.supersmashbros.core.State;

/**
 * 
 * 
 * @author Khyber Sen
 */
//An attack has hitboxes
public abstract class Attack extends Action {
    
    public final float damage;
    public final float angle;
    public final float knockback;
    
    public Attack(final State state, final KeyBinding keyBinding, final State[] impossiblePreStates,
            final float startup, final float duration, final float cooldown, final float damage,
            final float angle, final float knockback) {
        super(state, keyBinding, impossiblePreStates, startup, duration, cooldown);
        this.damage = damage;
        this.angle = angle;
        this.knockback = knockback;
    }
    
    @Override
    protected abstract void attack(final State state);
    
    @Override
    protected final void move(final Vector2 velocity, boolean isOnPlatform) {}
    
}
