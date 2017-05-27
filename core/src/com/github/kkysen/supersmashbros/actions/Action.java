package com.github.kkysen.supersmashbros.actions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.github.kkysen.libgdx.util.KeyBinding;
import com.github.kkysen.supersmashbros.core.Hitbox;
import com.github.kkysen.supersmashbros.core.State;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 
 * 
 * @author Khyber Sen
 */
//@RequiredArgsConstructor
public abstract class Action {
    private final State state;
    public final KeyBinding keyBinding;
    protected final float startup;
    protected final float duration;
    protected final float cooldown;
    
    @Getter
    private final Array<Hitbox> hitboxes;
    
    protected Action(final State st, final KeyBinding binding, final Array<Hitbox> h,
    		final float s, final float d, final float c) {
    	state = st;
    	keyBinding = binding;
    	hitboxes = h;
    	startup = s;
    	duration = s+d;
    	cooldown = s+d+c;
    }
    
    
    
    protected float elapsedTime;
    
    public State execute(final State state, final Vector2 acceleration,
            final Vector2 velocity) {
        if (elapsedTime < cooldown) {
            elapsedTime += Gdx.graphics.getDeltaTime();
            return state;
        }
        elapsedTime = 0;
        return state;
    }
    
    public abstract float getBaseDmg();
    public abstract float getAngle();
    public abstract float getKnockBack();
}
