package com.github.kkysen.supersmashbros.core;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
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
    
    private final Animation<T> animation;
    
    final Array<Hitbox> hitboxes = new Array<>();
    final Array<Hurtbox> hurtboxes = new Array<>();
    
    protected abstract Animation<T> getAnimation();
    
    protected State() {
        animation = getAnimation();
    }
    
    @Override
    public void render(final Batch batch) {
        
        // TODO
    }
    
}
