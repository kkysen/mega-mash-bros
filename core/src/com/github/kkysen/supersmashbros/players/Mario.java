package com.github.kkysen.supersmashbros.players;

import static com.github.kkysen.supersmashbros.app.Game.asset;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.github.kkysen.libgdx.util.Textures;
import com.github.kkysen.libgdx.util.keys.Controller;
import com.github.kkysen.libgdx.util.keys.KeyBinding;
import com.github.kkysen.libgdx.util.keys.User;
import com.github.kkysen.supersmashbros.actions.DownTiltAttack;
import com.github.kkysen.supersmashbros.actions.Executable;
import com.github.kkysen.supersmashbros.actions.ForwardAirAttack;
import com.github.kkysen.supersmashbros.actions.ForwardTiltAttack;
import com.github.kkysen.supersmashbros.actions.Jump;
import com.github.kkysen.supersmashbros.actions.Message;
import com.github.kkysen.supersmashbros.actions.MoveLeft;
import com.github.kkysen.supersmashbros.actions.MoveRight;
import com.github.kkysen.supersmashbros.actions.RangeAttack;
import com.github.kkysen.supersmashbros.actions.Stop;
import com.github.kkysen.supersmashbros.ai.FrozenAI;
import com.github.kkysen.supersmashbros.ai.JumpAI;
import com.github.kkysen.supersmashbros.ai.RandomAI;
import com.github.kkysen.supersmashbros.ai.SmartAI;
import com.github.kkysen.supersmashbros.core.Player;
import com.github.kkysen.supersmashbros.core.State;

/**
 * 
 * 
 * @author Khyber Sen
 */
public class Mario extends Player {
    
    private static final FileHandle sprites = asset("sprites_transparent.png");
    private static Array<TextureRegion> temp;
    
    
    
    private static final State idleRight = new State("Mario idle state",
            new Animation<>(
                    0.2f,
                    Textures.getFrames(
                            new Texture(sprites),
                            6,
                            16, 24, 27, 38),
                    PlayMode.LOOP_PINGPONG));
    
    static {
    	temp = Textures.getFrames(
                new Texture(sprites),
                6,
                16, 24, 27, 38);
    	temp.forEach((x) -> x.flip(true, false));
    }
    
    private static final State idleLeft = new State("Mario idle state",
            new Animation<>(
                    0.2f,
                    temp,
                    PlayMode.LOOP_PINGPONG));
    
    private static final State moveRightState = new State("Mario move right state",
            new Animation<>(
                    0.1f,
                    Textures.getFrames(
                            new Texture(sprites),
                            8,
                            10, 147, 32, 38),
                    PlayMode.LOOP));
    
    //I know this is dirty
    static {
    	temp = Textures.getFrames(
                new Texture(asset("sprites_transparent.png")),
                8,
                10, 147, 32, 38);
    	
    	temp.forEach((x) -> x.flip(true, false));
    }   
    
    private static final State moveLeftState = new State("Mario move right state",
            new Animation<>(
                    0.1f,
                    temp,
                    PlayMode.LOOP));
    
    private static final State jumpState = new State("Mario jump state",
            new Animation<>(
                    0.5f,
                    Textures.getFrames(
                            new Texture(sprites),
                            17, 84,
                            new int[][] {
                                {28, 42},
                                {31, 42},
                                {33, 44}
                            })));
    
    private static final State forwardTiltState = new State("Mario forward tilt state",
            new Animation<>(
                    0.1f,
                    Textures.getFrames(
                            new Texture(sprites),
                            11, 998,
                            new int[][] {
                                {32, 38},
                                {51, 38},
                                {45, 38},
                                {42, 38},
                                {40, 38},
                                {38, 44},
                                {39, 38},
                                {39, 38},
                                {31, 38}
                            })));
    
    private static final State downTiltState = new State("Mario down tilt state",
            new Animation<>(
                    0.1f,
                    Textures.getFrames(
                            new Texture(sprites),
                            12, 788,
                            new int[][] {
                                {29, 36},
                                {56, 36},
                                {39, 36},
                                {43, 36},
                                {33, 36},
                                {34, 36}
                            })));
    
    static {
    	temp = Textures.getFrames(
                new Texture(sprites),
                14, 1068,
                new int[][] {
                    {38, 40},
                    {37, 40},
                    {37, 40},
                    {38, 40},
                    {35, 40},
                    {50, 40},
                    {48, 40},
                    {38, 40},
                    {35, 40},
                    {40, 40},
                    {42, 40}
                });
    	
        Array<TextureRegion> temp2 = Textures.getFrames(
                new Texture(sprites),
                12, 1120,
                new int[][] {
                	{41, 40},
                    {41, 40},
                    {42, 40},
                    {42, 40},
                    {40, 40}
                });
        temp.addAll(temp2);
    }
    
    private static final State forwardAirState = new State("Mario forward air state",
            new Animation<>(
                    0.1f,
                    temp));
    		
    
    public static Mario userControlled() {
        return new Mario(User.get());
    }
    
    public static Mario randomlyControlled() {
        return new Mario(new RandomAI());
    }
    
    public static Mario frozen() {
        return new Mario(new FrozenAI());
    }
    
    public static Mario jump() {
        return new Mario(new JumpAI());
    }
    
    public static Mario smart() {
        return new Mario(new SmartAI());
    }
    
    public Mario(final Controller controller) {
        super("Mario", controller, idleRight, 1, new Executable[] {
            new Stop(idleRight),
            new MoveLeft(moveLeftState, 0f, 200f),
            new MoveRight(moveRightState, 0f, 200f),
            new Jump(jumpState, 1f, 0.1f, 500f),
            new RangeAttack(idleRight, 0, 1f, 1f, 5f, 5f),
            new ForwardTiltAttack(forwardTiltState, 0.1f, 0.1f, .5f, 5f, 5f),
            new Message(KeyBinding.P, player -> player.position),
            new DownTiltAttack(downTiltState, 0.1f, 0.1f, 0.3f, 3f, 1f),
            new ForwardAirAttack(forwardAirState, 0.6f, 0.1f, .8f, 3f, 1f)
        });
    }
    
}
