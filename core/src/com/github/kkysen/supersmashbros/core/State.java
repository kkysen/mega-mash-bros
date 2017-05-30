package com.github.kkysen.supersmashbros.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.github.kkysen.libgdx.util.ExtensionMethods;
import com.github.kkysen.libgdx.util.Renderable;

import lombok.experimental.ExtensionMethod;

/**
 * 
 * 
 * @author Khyber Sen
 */
@ExtensionMethod(ExtensionMethods.class)
public class State implements Renderable {
    
    private Player player;
    public Vector2 position;
    
    private float elapsedTime = 0;
    private final Animation<Texture> animation;
    
    public State(final Animation<Texture> animation) {
        this.animation = animation;
    }
    
    public void setPlayer(final Player player) {
        this.player = player;
        position = player.position;
    }
    
    @Override
    public void render(final Batch batch) {
        elapsedTime += Gdx.graphics.getDeltaTime();
        final Texture frame = animation.getKeyFrame(elapsedTime);
        batch.draw(frame, position.x, position.y);
        // TODO
    }
    
    public Hitbox newHitbox(final float lifetime, final float damage, final Vector2 center,
            final float width, final float height, final float vx, final float vy) {
        final Hitbox hitbox = new Hitbox(player, lifetime, damage);
        hitbox.bounds.setPositionAndSize(center, width, height);
        hitbox.velocity.set(vx, vy);
        return hitbox;
    }
    
    public void addHitbox(final Hitbox hitbox) {
        player.hitboxes.add(hitbox);
    }
    
    public void addHurtbox(final Hurtbox hurtbox) {
        player.hurtboxes.add(hurtbox);
    }
    
}
