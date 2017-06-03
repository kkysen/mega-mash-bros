package com.github.kkysen.supersmashbros.core;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pools;
import com.github.kkysen.libgdx.util.ExtensionMethods;
import com.github.kkysen.supersmashbros.actions.Attack;

import lombok.experimental.ExtensionMethod;

/**
 * 
 * 
 * @author Khyber Sen
 */
@ExtensionMethod(ExtensionMethods.class)
public class Hitbox extends Box {
    
    public final Player player;
    
    public final Attack attack;
    
    private final Vector2 position;
    public final Vector2 velocity = Pools.obtain(Vector2.class);
    public final Vector2 acceleration = Pools.obtain(Vector2.class);
    
    public Hitbox(final Player player, final Attack attack, final float width, final float height) {
        super(player.position, width, height, attack.duration);
        this.player = player;
        this.attack = attack;
        position = player.position.cpy();
    }
    
    @Override
    protected Color getColor() {
        return Color.RED;
    }
    
    @Override
    public String toString() {
        return player + "'s Hitbox[damage = " + attack.damage + "]";
    }
    
    public String motion() {
        return "a = " + acceleration + ", v = " + velocity + ", p = " + position;
    }
    
    @Override
    public boolean subUpdate() {
        log("updated vectors");
        //System.out.println(join(acceleration, velocity, position));
        acceleration.accelerate(velocity, position);
        bounds.x = position.x;
        bounds.y = position.y;
        return true;
    }
    
}
