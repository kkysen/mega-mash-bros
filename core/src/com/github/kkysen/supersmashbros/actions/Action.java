package com.github.kkysen.supersmashbros.actions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.github.kkysen.libgdx.util.KeyBinding;
import com.github.kkysen.libgdx.util.Loggable;
import com.github.kkysen.supersmashbros.core.Hitbox;
import com.github.kkysen.supersmashbros.core.State;

/**
 * 
 * 
 * @author Khyber Sen
 */
public abstract class Action implements Loggable {
    
    private final State state;
    public final KeyBinding keyBinding;
    
    protected final float startup;
    protected final float duration;
    protected final float cooldown;
    
    public final float baseDamage;
    public final float angle;
    public final float knockback;
    
    public final Array<Hitbox> hitboxes;
    
    protected float elapsedTime;
    
    protected Action(final State state, final KeyBinding binding, final Array<Hitbox> hitboxes,
            final float startup, final float duration, final float cooldown, final float baseDamage,
            final float angle, final float knockback) {
        this.state = state;
        keyBinding = binding;
        this.hitboxes = hitboxes;
        this.startup = startup;
        this.duration = startup + duration;
        this.cooldown = startup + duration + cooldown;
        this.baseDamage = baseDamage;
        this.angle = angle;
        this.knockback = knockback;
    }
    
    public State execute(final State state, final Vector2 acceleration,
            final Vector2 velocity) {
        if (elapsedTime < cooldown) {
            log(this + " still in cooldown");
            elapsedTime += Gdx.graphics.getDeltaTime();
            return state;
        }
        log(this + "ing");
        elapsedTime = 0;
        return state;
    }
    
}
