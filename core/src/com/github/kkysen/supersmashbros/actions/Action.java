package com.github.kkysen.supersmashbros.actions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.github.kkysen.libgdx.util.KeyBinding;
import com.github.kkysen.supersmashbros.core.Hitbox;
import com.github.kkysen.supersmashbros.core.State;

import lombok.Getter;

/**
 * 
 * 
 * @author Khyber Sen
 */
//@RequiredArgsConstructor
public abstract class Action {
    
    private final State state;
    public final KeyBinding keyBinding;
    
    protected final float startup;
    protected final float duration;
    protected final float cooldown;
    
    private final @Getter Array<Hitbox> hitboxes;
    
    protected float elapsedTime;
    
    protected Action(final State state, final KeyBinding binding, final Array<Hitbox> hitboxes,
            final float startup, final float duration, final float cooldown) {
        this.state = state;
        keyBinding = binding;
        this.hitboxes = hitboxes;
        this.startup = startup;
        this.duration = startup + duration;
        this.cooldown = startup + duration + cooldown;
    }
    
    public State execute(final State state, final Vector2 acceleration,
            final Vector2 velocity) {
        if (elapsedTime < cooldown) {
            elapsedTime += Gdx.graphics.getDeltaTime();
            return state;
        }
        elapsedTime = 0;
        return state;
    }
    
    public abstract float getBaseDmg();
    
    public abstract float getAngle();
    
    public abstract float getKnockBack();
    
}
