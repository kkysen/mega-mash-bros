package com.github.kkysen.supersmashbros.players;

import static com.github.kkysen.supersmashbros.app.Game.asset;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.github.kkysen.libgdx.util.Textures;
import com.github.kkysen.libgdx.util.keys.Controller;
import com.github.kkysen.libgdx.util.keys.User;
import com.github.kkysen.supersmashbros.actions.Action;
import com.github.kkysen.supersmashbros.actions.Jump;
import com.github.kkysen.supersmashbros.actions.MoveLeft;
import com.github.kkysen.supersmashbros.actions.MoveRight;
import com.github.kkysen.supersmashbros.actions.SmashAttack;
import com.github.kkysen.supersmashbros.actions.Stop;
import com.github.kkysen.supersmashbros.ai.FrozenAI;
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
    
    private static Array<TextureRegion> temp = Textures.getFrames(
            new Texture(asset("sprites_transparent.png")),
            8,
            10, 147, 32, 38);
    
    //I know this is dirty
    static {
    	temp.forEach((x) -> x.flip(true, false));
    }   
    
    private static final State moveLeftState = new State("Mario move right state",
            new Animation<>(
                    0.1f,
                    temp,
                    PlayMode.LOOP));
    
    private static final State jumpState = new State("Mario jump state",
            new Animation<>(
                    0.1f,
                    Textures.getFrames(
                            new Texture(asset("sprites_transparent.png")),
                            17, 84,
                            new int[][] {
                                {28, 42},
                                {31, 42},
                                {33, 44}
                            })));
    
    public static Mario userControlled() {
        return new Mario(User.get());
    }
    
    public static Mario randomlyControlled() {
        return new Mario(new RandomAI());
    }
    
    public static Mario frozen() {
        return new Mario(new FrozenAI());
    }
    
    public Mario(final Controller controller) {
        super("Mario", controller, idleState, new Stop(idleState, jumpState), 1, new Action[] {
            new Stop(idleState, jumpState),	//FIXME not technically a "move," but leave it for now
            new MoveLeft(moveLeftState, 0f, 100f),
            new MoveRight(moveRightState, 0f, 100f),
            new Jump(jumpState, 1f, 0.1f, 300f),
            new SmashAttack(idleState, 0, 10f, 1f, 5f, 5f)
        });
    }
    
}
