package com.github.kkysen.supersmashbros.core;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.github.kkysen.libgdx.util.Renderable;

/**
 * 
 * 
 * @author Khyber Sen
 */
public class Platform extends Box implements Renderable {
    
    private final Sprite sprite;
    
    public Platform(final Sprite sprite) {
        super(sprite.getBoundingRectangle());
        this.sprite = sprite;
    }
    
    @Override
    public void render(final Batch batch) {
        sprite.draw(batch);
    }
    
}
