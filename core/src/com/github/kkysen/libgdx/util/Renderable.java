package com.github.kkysen.libgdx.util;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * 
 * 
 * @author Khyber Sen
 */
public interface Renderable {
    
    public void render(Batch batch);
    
    public default void render(final ShapeRenderer lineRenderer, Camera camera) {}
    
}
