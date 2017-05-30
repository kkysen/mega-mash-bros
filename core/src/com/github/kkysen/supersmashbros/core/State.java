package com.github.kkysen.supersmashbros.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.github.kkysen.libgdx.util.Renderable;

/**
 * 
 * 
 * @author Khyber Sen
 */
public class State implements Renderable {
    
    private final Player player;
    
    private float elapsedTime = 0;
    private final Animation<Texture> animation;
    
    public final Vector2 position = new Vector2();
    
    protected State(final Player player, final Animation<Texture> animation) {
        this.player = player;
        this.animation = animation;
    }
    
    @Override
    public void render(final Batch batch) {
        elapsedTime += Gdx.graphics.getDeltaTime();
        final Texture frame = animation.getKeyFrame(elapsedTime);
        batch.draw(frame, position.x, position.y);
        // TODO
    }
    
    protected Hitbox newHitbox(final float lifetime, final float damage) {
        return new Hitbox(player, lifetime, damage);
    }
    
    // should be abstract
    public void addHitboxes(final Array<Hitbox> hitboxes) {
        // probably shouldn't delete any hitboxes
        final float lifetime = 0; // FIXME
        final float damage = 0; // FIXME
        hitboxes.add(newHitbox(lifetime, damage));
        // set hitbox.rectangle
        // set hitbox.damage
        // set hitbox.acceleration
        // set hitbox.velocity
        // TODO
    }
    
    // should be abstract
    public void addHurtboxes(final Array<Hurtbox> hurtboxes) {
        // TODO see addHitboxes above
    }
    
}
