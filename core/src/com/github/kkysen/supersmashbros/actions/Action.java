package com.github.kkysen.supersmashbros.actions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.github.kkysen.libgdx.util.ExtensionMethods;
import com.github.kkysen.libgdx.util.Loggable;
import com.github.kkysen.libgdx.util.keys.KeyBinding;
import com.github.kkysen.supersmashbros.core.Player;
import com.github.kkysen.supersmashbros.core.State;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.ExtensionMethod;

/**
 * 
 * 
 * @author Khyber Sen
 */
@ExtensionMethod(ExtensionMethods.class)
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class Action implements Loggable {
    
    private final State state;
    public final KeyBinding keyBinding;
    
    private final State[] impossiblePreStates;
    
    protected final float startup;
    public final float duration;
    protected final float cooldown;
    
    protected float elapsedTime;
    
    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
    
    public void update() {
        elapsedTime += Gdx.graphics.getDeltaTime();
    }
    
    private boolean isImpossiblePreState(final State state) {
        for (final State impossiblePreState : impossiblePreStates) {
            if (state == impossiblePreState) { // I meant to use ==
                return true;
            }
        }
        return false;
    }
    
    public final State execute(final Player player) {
        if (elapsedTime < cooldown || isImpossiblePreState(player.state)) {
            error(this + " still in cooldown, " + (cooldown - elapsedTime) + " left");
            return player.state;
        }
        log("someone called " + this);
        elapsedTime = 0;
        state.setPlayer(player);
        attack(state);
        move(player.velocity);
        return state;
    }
    
    protected void attack(final State state) {}
    
    protected void move(final Vector2 velocity) {}
    
}
