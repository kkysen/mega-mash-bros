package com.github.kkysen.supersmashbros.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.github.kkysen.libgdx.util.ExtensionMethods;
import com.github.kkysen.libgdx.util.Loggable;
import com.github.kkysen.libgdx.util.Renderable;
import com.github.kkysen.supersmashbros.actions.Attack;

import lombok.experimental.ExtensionMethod;

/**
 * 
 * 
 * @author Khyber Sen
 */
@ExtensionMethod(ExtensionMethods.class)
public class State implements Renderable, Loggable {
    
    private final String name;
    
    private Player player;
    public Vector2 position;
    
    private float elapsedTime = 0;
    private final Animation<TextureRegion> animation;
    
    public State(final String name, final Animation<TextureRegion> animation) {
        this.name = name;
        this.animation = animation;
    }
    
    @Override
    public String toString() {
        return name;
    }
    
    public void setPlayer(final Player player) {
        this.player = player;
        error(this + " set player to " + player);
        position = player == null ? null : player.position;
        elapsedTime = 0;
    }
    
    @Override
    public void render(final Batch batch) {
        elapsedTime += Gdx.graphics.getDeltaTime();
        final TextureRegion frame = animation.getKeyFrame(elapsedTime);
        batch.draw(frame, position.x, position.y);
        // TODO
        //rendering hitboxes here?
        drawBoxes(ShapeType.Line, Color.GREEN, player.hitboxes);
        drawBoxes(ShapeType.Line, Color.RED, player.hurtboxes);
    }
    
    public void drawBoxes(final ShapeType shapeType, final Color color,
            final Array<? extends Box> boxes) {
        shapeRenderer.begin(shapeType);
        shapeRenderer.setColor(color);
        for (final Box b : boxes) {
            //ill fix this, dont you worry
            shapeRenderer.rect(b.bounds.x, b.bounds.y,
                    b.bounds.width, b.bounds.height);
        }
        shapeRenderer.end();
    }
    
    public Hitbox newHitbox(final Attack attack) {
        final Hitbox hitbox = new Hitbox(player, attack);
        hitbox.bounds.setPosition(position);
        return hitbox;
    }
    
    public void addHitbox(final Hitbox hitbox) {
        player.hitboxes.add(hitbox);
    }
    
    public void addHurtbox(final Hurtbox hurtbox) {
        player.hurtboxes.add(hurtbox);
    }
    
}
