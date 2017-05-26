package com.github.kkysen.libgdx.util;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pools;

/**
 * 
 * 
 * @author Khyber Sen
 */
public class ExtensionMethods {
    
    public static Rectangle scale(final Rectangle rectangle, final float scalar) {
        final Vector2 center = rectangle.getCenter(Pools.obtain(Vector2.class));
        final float width = rectangle.width * scalar;
        final float height = rectangle.height * scalar;
        return Pools.obtain(Rectangle.class)//
                .set(center.x - width * 0.5f, center.y - height * 0.5f, width, height);
    }
    
}
