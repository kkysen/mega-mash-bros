package com.github.kkysen.supersmashbros.players;

import static com.github.kkysen.supersmashbros.app.SuperSmashBros.asset;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.github.kkysen.libgdx.util.Textures;
import com.github.kkysen.libgdx.util.keys.Controller;
import com.github.kkysen.libgdx.util.keys.User;
import com.github.kkysen.supersmashbros.actions.Action;
import com.github.kkysen.supersmashbros.actions.Jump;
import com.github.kkysen.supersmashbros.actions.MoveLeft;
import com.github.kkysen.supersmashbros.actions.MoveRight;
import com.github.kkysen.supersmashbros.actions.SmashAttack;
import com.github.kkysen.supersmashbros.ai.RandomAI;
import com.github.kkysen.supersmashbros.core.Player;
import com.github.kkysen.supersmashbros.core.State;

/**
 * 
 * 
 * @author Khyber Sen
 */
public class Mario extends Player {
    
    private static final State idleState = new State("Mario idle state",
            new Animation<>(
                    0.2f,
                    Textures.getFrames(
                            new Texture(asset("sprites_transparent.png")),
                            6,
                            16, 24, 27, 38),
                    PlayMode.LOOP_PINGPONG));
    
    private static final State moveRightState = new State("Mario move right state",
            new Animation<>(
                    0.1f,
                    Textures.getFrames(
                            new Texture(asset("sprites_transparent.png")),
                            8,
                            10, 147, 32, 38),
                    PlayMode.LOOP));
    
    public static Mario userControlled() {
        return new Mario(User.get());
    }
    
    public static Mario randomlyControlled() {
        return new Mario(new RandomAI());
    }
    
    public Mario(final Controller controller) {
        super("Mario", controller, moveRightState, 1, new Action[] {
            new MoveLeft(idleState, 0f, 100f),
            new MoveRight(moveRightState, 0f, 100f),
            new Jump(idleState, 1f, 0.1f, 300f),
            new SmashAttack(idleState, 0, 2f, 3f, 5f, 5f)
        });
    }
    
}
