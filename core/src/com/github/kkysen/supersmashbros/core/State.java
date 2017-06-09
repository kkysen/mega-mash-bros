package com.github.kkysen.supersmashbros.core;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.github.kkysen.libgdx.util.ExtensionMethods;
import com.github.kkysen.libgdx.util.Loggable;
import com.github.kkysen.libgdx.util.Renderable;
import com.github.kkysen.supersmashbros.actions.Action;
import com.github.kkysen.supersmashbros.actions.Attack;
import com.github.kkysen.supersmashbros.app.Game;

import lombok.experimental.ExtensionMethod;

/**
 * @author Khyber Sen
 */
@ExtensionMethod(ExtensionMethods.class)
public class State implements Renderable, Loggable, Cloneable {
    
    private final String name;
    
    private Player player;
    public Action action;
    public Vector2 position;
    
    public boolean resetJustCalled;
    private float elapsedTime;
    private final Animation<TextureRegion> animation;
    private TextureRegion lastFrame;
    
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
    
    public void setPlayer(final Player player) {
        this.player = player;
        error(this + " set player to " + player);
        position = player == null ? null : player.position;
        elapsedTime = 0;
        //elapsedTime = player == null ? 0 : elapsedTime;
    }
    
    public void setPlayer(final Player player, boolean resetTime) {
        this.player = player;
        error(this + " set player to " + player);
        position = player == null ? null : player.position;
        if (resetTime) elapsedTime = 0;
    }
    
    @Override
    public void render(final Batch batch) {
        elapsedTime += Game.deltaTime;
        //System.out.println(elapsedTime);
        final TextureRegion frame = animation.getKeyFrame(elapsedTime);
        
        //spazes if you use this
        //if (player.facingRight) frame.flip(true, false);
        
        batch.draw(frame, position.x, position.y);
        // TODO
    }
    
    public Hitbox newHitbox(final Attack attack, final float width, final float height) {
        return new Hitbox(player, attack, width, height);
    }
    
    public void addHitbox(final Hitbox hitbox) {
        player.hitboxes.add(hitbox);
    }
    
    public void addHurtbox(final Hurtbox hurtbox) {
        player.hurtboxes.add(hurtbox);
    }
    
    public void resetTime() {
    	elapsedTime = 0;
    }
    
}