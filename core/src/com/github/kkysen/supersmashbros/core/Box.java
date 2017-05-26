package com.github.kkysen.supersmashbros.core;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pool.Poolable;

/**
 * 
 * 
 * @author Khyber Sen
 */
public class Box implements Poolable {
    
    private static final Pool<Rectangle> pool = new Pool<Rectangle>() {
        
        @Override
        public Rectangle newObject() {
            return new Rectangle();
        }
        
    };
    
    private final Rectangle rectangle;
    
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
    
}
