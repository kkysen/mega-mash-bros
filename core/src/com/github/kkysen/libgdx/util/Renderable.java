package com.github.kkysen.libgdx.util;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * 
 * 
 * @author Khyber Sen
 */
public interface Renderable {
    
    static ShapeRenderer shapeRenderer = new ShapeRenderer();
    
    public void render(Batch batch);
    
}
