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
public class Action extends Executable implements Loggable {
    
    private final State state;
    
    private final State[] impossiblePreStates;
    
    public final float startup;
    public final float duration;
    protected final float cooldown;
    
    protected float elapsedTime;
    public boolean firstCalled;
    protected int timesUsed;
    
    protected Action(final State state, final KeyBinding keyBinding,
            final State[] impossiblePreStates, final float warmupTime, final float duration,
            final float cooldown) {
        super(keyBinding);
        this.state = state.clone();
        this.impossiblePreStates = impossiblePreStates;
        startup = warmupTime;
        this.duration = duration;
        this.cooldown = cooldown;
        firstCalled = true;
        timesUsed = 0;
    }
    
    public void update() {
        elapsedTime += Game.deltaTime;
    }
    
    private boolean isImpossiblePreState(final State state) {
        for (final State impossiblePreState : impossiblePreStates) {
        	//System.out.println("impState: " + impossiblePreState);
        	//System.out.println("state: " + state);
        	//System.out.println("prestate" + state.toString().equals(impossiblePreState.toString()));
            if (state.toString().equals(impossiblePreState.toString())) { // I meant to use ==
                return true;
            }
        }
        return false;
    }
    
    @Override
    public final State execute(final Player player) {
    	//System.out.print
    	/*if (elapsedTime < startup || isImpossiblePreState(player.state)) {
        	System.out.println(elapsedTime);
        	System.out.println("start");
            error(this + " still in cooldown, " + (cooldown - elapsedTime) + " left");
            return player.state;
        }*/
    	
        if (elapsedTime < cooldown || isImpossiblePreState(player.state)) {
        	System.out.println(elapsedTime);
        	System.out.println("cool");
            error(this + " still in cooldown, " + (cooldown - elapsedTime) + " left");
            return player.state;
        }
        
        
        error("someone called " + this);
        
        if (firstCalled) {
        	player.state.setPlayer(null, true);
        	//System.out.println("first");
        	elapsedTime = 0;
        	state.setPlayer(player, true);
            firstCalled = false;
        }
        else {
        	//System.out.println("held");
        	state.setPlayer(player, false);
        }
        
        //If we want to do cool stuff later
        if (this instanceof MoveRight) {
        	player.facingRight = true;
        }
        else if (this instanceof MoveLeft) {
        	player.facingRight = false;
        }
        
        //if (timesUsed == 0) {
        	attack(state);
            move(player.acceleration, player.velocity, player.isOnPlatform());
        /*}
        
        if (this instanceof Attack && timesUsed == 0) {
            timesUsed++;
        }*/
        
        
        return state;
    }
    
    public void reset() {
    	firstCalled = true;
    	timesUsed = 0;
    }
    
    protected void attack(final State state) {}
    
    protected void move(final Vector2 acceleration, final Vector2 velocity,
            final boolean isOnPlatform) {}
    
    
}
