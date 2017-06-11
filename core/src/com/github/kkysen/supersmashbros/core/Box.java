package com.github.kkysen.supersmashbros.core;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.badlogic.gdx.utils.Pools;
import com.github.kkysen.libgdx.util.Debuggable;
import com.github.kkysen.libgdx.util.Renderable;
import com.github.kkysen.supersmashbros.app.Game;

/**
 * 
 * 
 * @author Khyber Sen
 */
public abstract class Box implements Renderable, Poolable, Debuggable {
    
    private static final Pool<Rectangle> pool = Pools.get(Rectangle.class);
    
    public final Rectangle bounds = pool.obtain();
    
    public final Player player;
    
    public float elapsedTime;
    private final float lifetime;
    
    public Box(final Player player, final float width, final float height, final float lifetime,
            final float warmupTime) {
        this.player = player;
        bounds.x = player.position.x;
        bounds.y = player.position.y;
        bounds.width = width;
        bounds.height = height;
        this.lifetime = lifetime;
        //elapsedTime = -warmupTime;
        elapsedTime = 0;
    }
    
    @Override
    public void reset() {
        pool.free(bounds);
    }
    
    public boolean isWarmingUp() {
        return elapsedTime < 0;
    }
    
    public final boolean overlaps(final Box box) {
        return bounds.overlaps(box.bounds);
    }
    
    public final Rectangle intersect(final Box box) {
        final Rectangle intersection = pool.obtain();
        if (Intersector.intersectRectangles(bounds, box.bounds, intersection)) {
            return intersection;
        } else {
            return null;
        }
    }
    
    public final float intersectionArea(final Box box) {
        final Rectangle intersection = intersect(box);
        return intersection == null ? 0 : intersection.area();
    }
    
    /**
     * @return true if the box still exists, false if it should be removed
     */
    public abstract boolean subUpdate();
    
    public final boolean update() {
        elapsedTime += Game.deltaTime;
        if (elapsedTime > lifetime || !player.world.bounds.contains(bounds.x, bounds.y)) {
            return false;
        }
        //System.out.println("derrr");
        return subUpdate();
    }
    
    @Override
    public final void render(final Batch batch) {}
    
    @Override
    public void render(final ShapeRenderer lineRenderer) {
        if (isWarmingUp()) { // still starting up
        	//System.out.println("warming up");
            return;
        }
        //System.out.println("rendering");
        lineRenderer.setColor(getColor());
        lineRenderer.rect(bounds.x, bounds.y, bounds.width, bounds.height);
    }
    
    protected abstract Color getColor();
    
}
