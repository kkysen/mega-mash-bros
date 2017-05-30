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
    protected final float duration;
    protected final float cooldown;
    
    protected float elapsedTime;
    
    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
    
    public void update() {
        elapsedTime += Gdx.graphics.getDeltaTime();
    }
    
    public final State execute(final Player player) {
        if (elapsedTime < cooldown || impossiblePreStates.contains(player.state)) {
            error(this + " still in cooldown, " + (cooldown - elapsedTime) + " left");
            return player.state;
        }
        log("someone called " + this);
        elapsedTime = 0;
        state.setPlayer(player);
        attack(state, player.position);
        move(player.velocity);
        return state;
    }
    
    protected void attack(final State state, final Vector2 position) {}
    
    protected void move(final Vector2 velocity) {}
    
}
