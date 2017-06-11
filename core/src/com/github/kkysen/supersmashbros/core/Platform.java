package com.github.kkysen.supersmashbros.core;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Disposable;
import com.github.kkysen.libgdx.util.ExtensionMethods;
import com.github.kkysen.libgdx.util.Renderable;

import lombok.experimental.ExtensionMethod;

/**
 * 
 * 
 * @author Khyber Sen
 */
@ExtensionMethod(ExtensionMethods.class)
public class Platform implements Renderable, Disposable {
    
    private static final float DEFAULT_FRICTION = 0.5f; // FIXME
    private static final float MARGIN_PERCENTAGE = 0.01f;
    
    public Rectangle bounds;
    private final Sprite sprite;
    public final float friction;
    
    public final float left;
    public final float right;
    public final float top;
    public final float bottom;
    public final float margin;
    public final float leftMargin;
    public final float rightMargin;
    
    public Platform(final Sprite sprite, final float friction) {
        bounds = sprite.getBoundingRectangle();
        this.sprite = sprite;
        this.friction = friction;
        
        left = bounds.x;
        right = bounds.maxX();
        top = bounds.maxY();
        bottom = bounds.y;
        margin = bounds.width * MARGIN_PERCENTAGE;
        leftMargin = left + margin;
        rightMargin = right - margin;
    }
    
    public Platform(final Sprite sprite) {
        this(sprite, DEFAULT_FRICTION);
    }
    
    @Override
    public void render(final Batch batch) {
        sprite.draw(batch);
    }
    
    public enum Relation {
        MIDDLE,
        LEFT_MARGIN,
        RIGHT_MARGIN,
        OFF_LEFT,
        OFF_RIGHT,
    }
    
    public Relation xRelation(final float x) {
        if (x < left) {
            return Relation.OFF_LEFT;
        } else if (x < leftMargin) {
            return Relation.LEFT_MARGIN;
        } else if (x < rightMargin) {
            return Relation.MIDDLE;
        } else if (x < right) {
            return Relation.RIGHT_MARGIN;
        } else {
            return Relation.OFF_RIGHT;
        }
    }
    
    @Override
    public void dispose() {
        sprite.getTexture().dispose();
    }
    
}
