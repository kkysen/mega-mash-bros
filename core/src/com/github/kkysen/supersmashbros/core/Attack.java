package com.github.kkysen.supersmashbros.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.github.kkysen.libgdx.util.KeyBinding;
import com.github.kkysen.supersmashbros.actions.Action;

/**
 * 
 * 
 * @author Khyber Sen
 */
//An attack has hitboxes
public abstract class Attack extends Action {
	protected float dmg;
	protected float angle;
	protected float knockback;
	
	private boolean inCooldown;
	
    
    /*public Attack(final float cooldown, final float startup, Hitbox... boxes) {
    	Array<Hitbox> a = new Array();
    	for (Hitbox h : boxes) a.add(h);
    	this(cooldown, startup, a);
    }*/
    
    public Attack(final KeyBinding key, final float startup, final float duration,
    		final float cooldown, Array<Hitbox> boxes) {
        super(null, key, boxes, startup, duration, cooldown);
        inCooldown = false;
    }
    
    @Override
    public State execute(final State state, final Vector2 acceleration,
            final Vector2 velocity) {
    	
    	if (elapsedTime < startup) {
    		elapsedTime += Gdx.graphics.getDeltaTime();
    		return state;
    	}
    	else if (elapsedTime < duration) {
    		//Perform attack
    	}
    	else if (elapsedTime < cooldown) {
    		elapsedTime += Gdx.graphics.getDeltaTime();
    		return state;
    	}
    	
    	
    	return state;
    }
    
    @Override
    public float getBaseDmg() {
    	return dmg;
    }
    
    @Override
    public float getAngle() {
    	return angle;
    }
    
    @Override
    public float getKnockBack() {
    	return knockback;
    }
}
