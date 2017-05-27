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
	public Array<Hitbox> hitboxes;
    
    public Attack(final float cooldown, final float startup, Hitbox... boxes) {
        super(null, null, cooldown, startup);
        
        hitboxes = new Array();
        for (Hitbox h : boxes) hitboxes.add(h);
    }
    
}
