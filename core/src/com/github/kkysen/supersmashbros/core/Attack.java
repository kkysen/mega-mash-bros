package com.github.kkysen.supersmashbros.core;

import com.badlogic.gdx.utils.Array;
import com.github.kkysen.supersmashbros.actions.Action;

/**
 * 
 * 
 * @author Khyber Sen
 */
//An attack has hitboxes
public abstract class Attack extends Action {
	public float dmg;
	public float angle;
	
    
    /*public Attack(final float cooldown, final float startup, Hitbox... boxes) {
    	Array<Hitbox> a = new Array();
    	for (Hitbox h : boxes) a.add(h);
    	this(cooldown, startup, a);
    }*/
    
    public Attack(final float cooldown, final float startup, Array<Hitbox> boxes) {
        super(null, null, cooldown, startup, boxes);
    }
    
}
