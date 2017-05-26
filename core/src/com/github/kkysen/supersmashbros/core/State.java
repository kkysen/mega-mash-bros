package com.github.kkysen.supersmashbros.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.github.kkysen.libgdx.util.Renderable;

/**
 * 
 * 
 * @author Khyber Sen
 * @param <T> animation frame type
 */
public abstract class State<T> implements Renderable, Poolable {
    
    private float stateTime = 0;
    
    public final Vector2 position = new Vector2();
    
    private final Animation<T> animation;
    
    public final Array<Hitbox> hitboxes = new Array<>();
    public final Array<Hurtbox> hurtboxes = new Array<>();
    
    protected abstract Animation<T> getAnimation();
    
    protected State() {
        animation = getAnimation();
    }
    
    @Override
    public void render(final Batch batch) {
        stateTime += Gdx.graphics.getDeltaTime();
        final T frame = animation.getKeyFrame(stateTime);
        batch.draw(frame, position.x, position.y); // FIXME type T
        // TODO
    }
    
}
