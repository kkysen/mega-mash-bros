package com.github.kkysen.supersmashbros.core;

import com.github.kkysen.supersmashbros.actions.Action;

/**
 * 
 * 
 * @author Khyber Sen
 */
public class Hurtbox extends Box {
    
    private static final float DAMAGE_SCALE = 1.0f; // FIXME
    
    public float damageTakenBy(final Hitbox hitbox) {
        return intersectionArea(hitbox) * hitbox.damage * DAMAGE_SCALE;
    }
    
    public float collide(final Attack attack) {
    	//Assuming each move has only one hitbox for now
        final float damage = damageTakenBy(attack.hitboxes.get(0));
        attack.hitboxes.get(0).player.attacked(damage);
        return damage;
    }
    
    @Override
    public boolean update() {
        return true; // TODO
    }
    
}
