package com.github.kkysen.supersmashbros.actions;

import com.badlogic.gdx.math.Vector2;
import com.github.kkysen.libgdx.util.ExtensionMethods;
import com.github.kkysen.libgdx.util.Loggable;
import com.github.kkysen.libgdx.util.keys.KeyBinding;
import com.github.kkysen.supersmashbros.app.Game;
import com.github.kkysen.supersmashbros.core.Player;
import com.github.kkysen.supersmashbros.core.State;

import lombok.experimental.ExtensionMethod;

/**
 * 
 * 
 * @author Khyber Sen
 */
@ExtensionMethod(ExtensionMethods.class)
public class Action implements Loggable {
    
    private final State state;
    public final KeyBinding keyBinding;
    
    private final State[] impossiblePreStates;
    
    protected final float startup;
    public final float duration;
    protected final float cooldown;
    
    protected float elapsedTime;
    
    protected Action(final State state, final KeyBinding keyBinding,
            final State[] impossiblePreStates, final float startup, final float duration,
            final float cooldown) {
        this.state = state.clone();
        this.keyBinding = keyBinding;
        this.impossiblePreStates = impossiblePreStates;
        this.startup = startup;
        this.duration = duration;
        this.cooldown = cooldown;
    }
    
    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
    
    public void update() {
        elapsedTime += Game.deltaTime;
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
        player.state.setPlayer(null);
        error("someone called " + this);
        elapsedTime = 0;
        state.setPlayer(player);
        attack(state);
        move(player.velocity, player.isOnPlatform());
        return state;
    }
    
    public final State continueAction(final Player player) {
    	error("someone continuing to " + this);
    	
    	/*attackContinue(state);
    	moveContinue(player.velocity, player.isOnPlatform());*/
    	//state.setPlayer(player);
    	attack(state);
        move(player.velocity, player.isOnPlatform());
    	
        return state;
    }

	public final State finish (final Player player) {
    	error(this + " finishing");
    	
    	/*attackFinish(state);
    	moveFinish(player.velocity, player.isOnPlatform());*/
    	//state.setPlayer(player);
    	attack(state);
        move(player.velocity, player.isOnPlatform());
    	
        return state;
    }

	protected void attack(final State state) {}
    
    protected void move(final Vector2 velocity, final boolean isOnPlatform) {}
    
    
    private void attackContinue(State state2) {}

	private void moveContinue(Vector2 velocity, boolean onPlatform) {}
	
	private void attackFinish(State state2) {}
	
	private void moveFinish(Vector2 velocity, boolean onPlatform) {}
}
