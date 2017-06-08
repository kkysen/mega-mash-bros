package com.github.kkysen.supersmashbros.actions;

import com.badlogic.gdx.math.MathUtils;
import com.github.kkysen.libgdx.util.keys.KeyBinding;
import com.github.kkysen.supersmashbros.core.Player;
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
    
    private boolean alreadyUsed = false;
    
    /**
     * @param state the state the player will assume by calling this attack
     * @param keyBinding the key binding used to call this attack
     * @param impossiblePreStates the states the player cannot be in when
     *            calling this attack
     * @param startup the time between calling this attack and it being executed
     * @param duration the duration of this attack
     * @param cooldown the cooldown period before being able to use this attack
     *            again
     * @param damage the relative "damage" that will be done to the enemy in the
     *            form of increasing percentage and knockback
     * @param angle the angle in degrees the enemy will be knocked back at
     * @param knockback a relative knockback value that determines how far the
     *            enemy is knocked back
     */
    public Attack(final State state, final KeyBinding keyBinding, final State[] impossiblePreStates,
            final float startup, final float duration, final float cooldown, final float damage,
            final float angle, final float knockback) {
        super(state, keyBinding, impossiblePreStates, startup, duration, cooldown);
        this.damage = damage;
        this.angle = MathUtils.degreesToRadians * angle;
        this.knockback = knockback;
    }
    
    @Override
    public void reset() {
        alreadyUsed = false;
    }
    
    @Override
    protected void tryAttack(final State state, final boolean facingRight) {
        if (alreadyUsed) {
            return;
        }
        alreadyUsed = true;
        attack(state, facingRight);
    }
    
    protected abstract void attack(State state, boolean facingRight);
    
    @Override
    protected void move(final Player player) {}
    
}
