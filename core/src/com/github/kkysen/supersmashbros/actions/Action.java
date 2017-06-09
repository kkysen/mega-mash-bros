package com.github.kkysen.supersmashbros.actions;

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
public class Action extends Executable implements Loggable {
    
    private final State state;
    
    private final State[] impossiblePreStates;
    
    public final float startup;
    public final float duration;
    public final float cooldown;
    
    protected float elapsedTime;
    
    public boolean alreadyUsed = false;
    
    protected Action(final State state, final KeyBinding keyBinding,
            final State[] impossiblePreStates, final float warmupTime, final float duration,
            final float cooldown) {
        super(keyBinding);
        this.state = state.clone();
        this.state.action = this;
        this.impossiblePreStates = impossiblePreStates;
        startup = warmupTime;
        this.duration = duration;
        this.cooldown = cooldown;
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
    
    @Override
    public final State execute(final Player player) {
       /* if (elapsedTime < cooldown || isImpossiblePreState(player.state)) {
            error(this + " still in cooldown, " + (cooldown - elapsedTime) + " left");
            return player.state;
        }*/
        
        error("someone called " + this);
        
        if (!alreadyUsed && !(this instanceof Stop)) {
        	player.state.setPlayer(null);
        	System.out.println("lol");
        	elapsedTime = 0;
        	state.setPlayer(player);
        	alreadyUsed = true;
        }
        else {
        	player.state.setPlayer(null, false);
        	state.setPlayer(player, false);
        }
        
        tryAttack(state, player.facingRight);
        move(player);
        return state;
    }
    
    protected void tryAttack(final State state, final boolean facingRight) {}
    
    protected void move(final Player player) {}
    
    public void reset() {
        alreadyUsed = false;
        elapsedTime = 0;
    }
}
