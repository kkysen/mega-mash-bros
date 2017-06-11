package com.github.kkysen.supersmashbros.actions;

import com.badlogic.gdx.utils.Timer.Task;
import com.github.kkysen.libgdx.util.Debuggable;
import com.github.kkysen.libgdx.util.ExtensionMethods;
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
public class Action extends Executable implements Debuggable {
    
    protected static final float PI = (float) Math.PI;
    
    private final State state;
    
    private final State[] impossiblePreStates;
    
    public final float warmupTime;
    public final float duration;
    public final float cooldown;
    
    protected float elapsedTime;
    
    protected Action(final State state, final KeyBinding keyBinding,
            final State[] impossiblePreStates, final float warmupTime, final float duration,
            final float cooldown) {
        super(keyBinding);
        this.state = state.clone();
        this.state.action = this;
        this.impossiblePreStates = impossiblePreStates;
        this.warmupTime = warmupTime;
        this.duration = duration;
        this.cooldown = cooldown;
    }
    
    @Override
    public String toString() {
        return name() + " w/ state = " + state;
    }
    
    public float totalTime() {
        return warmupTime + duration + cooldown;
    }
    
    @Override
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
    
    /**
     * allows subclasses to stop the execution
     * 
     * @param player the player executing this action
     * @return true if the execution shouldn't happen
     */
    protected boolean dontExecute(final Player player) {
        return false;
    }
    
    @Override
    public final State execute(final Player player) {
        if (elapsedTime < cooldown || isImpossiblePreState(player.state) || dontExecute(player)) {
            error(this + " still in cooldown, " + (cooldown - elapsedTime) + " left");
            return player.state;
        }
        elapsedTime = 0;
        player.state.setPlayer(null);
        state.setPlayer(player);
        if (warmupTime == 0) {
            if (!(this instanceof Stop)) {
                System.out.println("calling " + this + " right away");
            }
            attack(state, player.facingRight);
            move(player);
        } else {
            System.out.println("calling " + this + " after " + warmupTime + " sec");
            player.tasks.clear();
            player.schedule(warmupTime, new Task() {
                
                @Override
                public void run() {
                    attack(state, player.facingRight);
                    move(player);
                }
                
            });
        }
        return state;
    }
    
    protected void attack(final State state, final boolean facingRight) {}
    
    protected void move(final Player player) {}
    
}
