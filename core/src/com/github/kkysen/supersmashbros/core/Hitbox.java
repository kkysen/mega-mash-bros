package com.github.kkysen.supersmashbros.core;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pools;
import com.github.kkysen.libgdx.util.ExtensionMethods;

import lombok.experimental.ExtensionMethod;

/**
 * 
 * 
 * @author Khyber Sen
 */
@ExtensionMethod(ExtensionMethods.class)
public class Hitbox extends Box {
    
    public final Player player;
    
    public final float damage;
    
    private final Vector2 position = Pools.obtain(Vector2.class);
    public final Vector2 velocity = Pools.obtain(Vector2.class);
    public final Vector2 acceleration = Pools.obtain(Vector2.class);
    
    public Hitbox(final Player player, final float lifetime, final float damage) {
        super(lifetime);
        this.player = player;
        this.damage = damage;
    }
    
    @Override
    public String toString() {
        return player + "'s Hitbox, damage = " + damage;
    }
    
    public void collide(final Hurtbox hurtbox, final float damage) {
        log(this + " collided with " + hurtbox + ", inflicting " + damage + " damage");
        player.attack(damage);
    }
    
    @Override
    public boolean subUpdate() {
        log("updated vectors");
        acceleration.accelerate(velocity, position);
        bounds.x = position.x;
        bounds.y = position.y;
        return true;
    }
    
}
