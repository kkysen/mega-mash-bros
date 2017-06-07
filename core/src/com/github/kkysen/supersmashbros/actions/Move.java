package com.github.kkysen.supersmashbros.actions;

import com.github.kkysen.libgdx.util.keys.KeyBinding;
import com.github.kkysen.supersmashbros.core.Player;
import com.github.kkysen.supersmashbros.core.State;

/**
 * 
 * 
 * @author Khyber Sen
 */
public abstract class Move extends Action {
    
    private static final float RUNNING_ACCELERATION_FACTOR = 0.1f;
    private static final float FLYING_ACCELERATION_FACTOR = 0.5f;
    
    protected final float maxSpeed;
    private final float runningAcceleration;
    private final float flyingAcceleration;
    
    public Move(final State state, final KeyBinding keyBinding, final State[] impossiblePreStates,
            final float startup, final float duration, final float cooldown, final float maxSpeed) {
        super(state, keyBinding, impossiblePreStates, startup, duration, cooldown);
        this.maxSpeed = maxSpeed;
        runningAcceleration = maxSpeed * RUNNING_ACCELERATION_FACTOR;
        flyingAcceleration = runningAcceleration * FLYING_ACCELERATION_FACTOR;
    }
    
    protected float adjustSpeed(final Player player) {
        return player.wasOnPlatform ? runningAcceleration : flyingAcceleration;
    }
    
    @Override
    protected abstract void move(Player player);
    
}
