package com.github.kkysen.supersmashbros.actions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.github.kkysen.libgdx.util.Loggable;
import com.github.kkysen.libgdx.util.keys.KeyBinding;
import com.github.kkysen.supersmashbros.core.Player;
import com.github.kkysen.supersmashbros.core.State;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

/**
 * 
 * 
 * @author Khyber Sen
 */
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class Action implements Loggable {
    
    private final State state;
    public final KeyBinding keyBinding;
    
    protected final float duration;
    protected final float cooldown;
    
    protected float elapsedTime;
    
    public final State execute(final Player player) {
        if (elapsedTime < cooldown) {
            log(this + " still in cooldown");
            elapsedTime += Gdx.graphics.getDeltaTime();
            return player.state;
        }
        log(this + "ing");
        elapsedTime = 0;
        state.setPlayer(player);
        modifyState(state, player.position);
        move(player.velocity);
        return state;
    }
    
    protected void modifyState(final State state, final Vector2 position) {}
    
    protected void move(final Vector2 velocity) {}
    
}
