package com.github.kkysen.supersmashbros.core;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.github.kkysen.libgdx.util.ExtensionMethods;
import com.github.kkysen.libgdx.util.Loggable;
import com.github.kkysen.libgdx.util.Renderable;
import com.github.kkysen.supersmashbros.actions.Attack;
import com.github.kkysen.supersmashbros.app.Game;
import lombok.experimental.ExtensionMethod;

/**
 * @author Khyber Sen
 */
@ExtensionMethod(ExtensionMethods.class)
public class State implements Renderable, Loggable, Cloneable {
    
    private final String name;
    
    public Player player;
    public Vector2 position;
    
    private float elapsedTime;
    private final Animation<TextureRegion> animation;
    
    public State(final String name, final Animation<TextureRegion> animation) {
        this.name = name;
        this.animation = animation;
    }
    
    @Override
    public State clone() {
        final State clone = new State(name, animation);
        clone.player = player;
        clone.position = position;
        clone.elapsedTime = elapsedTime;
        return clone;
    }
    
    @Override
    public String toString() {
        return name + " @ " + position;
    }
    
    public void setPlayer(final Player player, final boolean resetTime) {
        this.player = player;
        error(this + " set player to " + player);
        position = player == null ? null : player.position;
        if (resetTime) elapsedTime = 0;
    }
    
    @Override
    public void render(final Batch batch) {
    	//System.out.println(elapsedTime + ": " + name);
        elapsedTime += Game.deltaTime;
        final TextureRegion frame = animation.getKeyFrame(elapsedTime);
        batch.draw(frame, position.x, position.y);
        // TODO
    }
    
    public Hitbox newHitbox(final Attack attack, final float width, final float height) {
        return new Hitbox(player, attack, width, height);
    }
    
    public Hitbox newHitbox(final Attack attack, final float width, final float height,
    		final Vector2 pos) {
        return new Hitbox(player, attack, width, height, pos);
    }
    
    public void addHitbox(final Hitbox hitbox) {
        player.hitboxes.add(hitbox);
    }
    
    public void addHurtbox(final Hurtbox hurtbox) {
        player.hurtboxes.add(hurtbox);
    }
    
}