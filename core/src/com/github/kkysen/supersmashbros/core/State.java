package com.github.kkysen.supersmashbros.core;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.github.kkysen.libgdx.util.Debuggable;
import com.github.kkysen.libgdx.util.ExtensionMethods;
import com.github.kkysen.libgdx.util.Renderable;
import com.github.kkysen.supersmashbros.actions.Action;
import com.github.kkysen.supersmashbros.actions.Attack;
import com.github.kkysen.supersmashbros.app.Game;

import lombok.experimental.ExtensionMethod;

/**
 * @author Khyber Sen
 */
@ExtensionMethod(ExtensionMethods.class)
public class State implements Renderable, Debuggable, Cloneable {
    
    private final String name;
    
    public Player player;
    public Action action;
    public Vector2 position;
    
    public Vector2 size;
    
    public boolean resetJustCalled;
    private float elapsedTime;
    private final Animation<TextureRegion> animationRight;
    private final Animation<TextureRegion> animationLeft;
    private final boolean alreadyFlipped = false;	//false for not yet right, true for not yet left
    
    public State(final String name,
            final Animation<TextureRegion> animationRight) {
        this.name = name;
        this.animationRight = animationRight;
        animationLeft = flipFrames(animationRight);
        final TextureRegion firstFrame = animationRight.getKeyFrame(0);
        final float maxSide = Math.max(firstFrame.getRegionWidth(), firstFrame.getRegionHeight());
        size = new Vector2(maxSide, maxSide);
    }
    
    private Animation<TextureRegion> flipFrames(final Animation<TextureRegion> right) {
        final Array<TextureRegion> temp = new Array<>();
        for (final Object r : right.getKeyFrames()) {
            temp.add(new TextureRegion((TextureRegion) r));
        }
        
        for (int x = 0; x < temp.size; x++) {
            //temp[x].flip(true, false);
            temp.get(x).flip(true, false);
        }
        
        return new Animation<>(right.getFrameDuration(), temp, right.getPlayMode());
    }
    
    @Override
    public State clone() {
        final State clone = new State(name, animationRight);
        clone.player = player;
        clone.action = action;
        clone.position = position;
        clone.resetJustCalled = resetJustCalled;
        clone.elapsedTime = elapsedTime;
        //clone.lastFrame = lastFrame;
        return clone;
    }
    
    @Override
    public String toString() {
        return name + " state @ " + position;
    }
    
    public void resetTime() {
        elapsedTime = 0;
    }
    
    public void setPlayer(final Player player, final boolean resetTime) {
        this.player = player;
        error(this + " set player to " + player);
        position = player == null ? null : player.position;
        if (resetTime) {
            resetTime();
        }
    }
    
    public void setPlayer(final Player player) {
        setPlayer(player, true);
    }
    
    @Override
    public void render(final Batch batch) {
        final Animation<TextureRegion> animation = player.facingRight ? animationRight
                : animationLeft;
        elapsedTime += Game.deltaTime;
        batch.draw(animation.getKeyFrame(elapsedTime), position.x, position.y);
    }
    
    public Hitbox newHitbox(final Attack attack, final float width, final float height) {
        return new Hitbox(player, attack, width, height);
    }
    
    public void addHitbox(final Hitbox hitbox) {
        player.hitboxes.add(hitbox);
    }
    
    public void addHurtbox(final Hurtbox hurtbox) {
        player.hurtboxes.add(hurtbox);
    }
    
}