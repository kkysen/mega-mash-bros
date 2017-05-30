package com.github.kkysen.supersmashbros.core;

import com.github.kkysen.supersmashbros.actions.Attack;

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
    public float damageTakenBy(final Attack attack, final Hitbox hitbox) {
        return intersectionArea(hitbox) * attack.damage/* * DAMAGE_SCALE*/;
    }
    
    public float collide(final Attack attack, final Hitbox hitbox) {
        final float damage = damageTakenBy(attack, hitbox);
        //hitbox.player.attacked(damage);
        return damage;
    }
    
    @Override
    public boolean subUpdate() {
        return true; // TODO
    }
    
}
