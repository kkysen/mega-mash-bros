package com.github.kkysen.supersmashbros.core;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.badlogic.gdx.utils.Pools;

/**
 * 
 * 
 * @author Khyber Sen
 */
public abstract class Box implements Poolable {
    
    private static final Pool<Rectangle> pool = Pools.get(Rectangle.class);
    
    public final Rectangle rectangle;
    
    public Box(final Rectangle rectangle) {
        this.rectangle = rectangle;
    }
    
    public Box() {
        this(pool.obtain());
    }
    
    @Override
    public void reset() {
        pool.free(rectangle);
    }
    
    public boolean overlaps(final Box box) {
        return rectangle.overlaps(box.rectangle);
    }
    
    public Rectangle intersect(final Box box) {
        final Rectangle intersection = pool.obtain();
        if (Intersector.intersectRectangles(rectangle, box.rectangle, intersection)) {
            return intersection;
        } else {
            return null;
        }
    }
    
    public float intersectionArea(final Box box) {
        final Rectangle intersection = intersect(box);
        return intersection == null ? 0 : intersection.area();
    }
    
    /**
     * @return true if the box still exists, false if it should be removed
     */
    public abstract boolean update();
    
}
