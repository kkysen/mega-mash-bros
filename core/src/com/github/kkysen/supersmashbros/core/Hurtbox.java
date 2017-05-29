package com.github.kkysen.supersmashbros.core;

import com.github.kkysen.supersmashbros.actions.Action;

/**
 * 
 * 
 * @author Khyber Sen
 */
public class Hurtbox extends Box {
    
    private static final float DAMAGE_SCALE = 1.0f; // FIXME
    
    public Hurtbox(final float lifetime) {
        super(lifetime);
    }
    
    public float damageTakenBy(final Hitbox hitbox) {
        return intersectionArea(hitbox) * hitbox.damage * DAMAGE_SCALE;
    }
    
    public float collide(final Hitbox hitbox) {
        log(this + " collided with " + hitbox);
        final float damage = damageTakenBy(hitbox);
        hitbox.collide(this, damage);
        return damage;
    }
    
    /**
     * Damage dealt, simplified:
     * Base dmg (from action) * contact area with hurtbox
     */
    public float damageTakenBy(final Action action, final Hitbox hitbox) {
        return intersectionArea(hitbox) * action.baseDamage/* * DAMAGE_SCALE*/;
    }
    
    public float collide(final Action action, final Hitbox hitbox) {
        final float damage = damageTakenBy(action, hitbox);
        //hitbox.player.attacked(damage);
        return damage;
    }
    
    @Override
    public boolean subUpdate() {
        return true; // TODO
    }
    
}
