package com.github.kkysen.megamashbros.players;

import static com.github.kkysen.megamashbros.app.Game.asset;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.github.kkysen.libgdx.util.Textures;
import com.github.kkysen.libgdx.util.keys.Controller;
import com.github.kkysen.libgdx.util.keys.KeyBinding;
import com.github.kkysen.libgdx.util.keys.User;
import com.github.kkysen.megamashbros.actions.DownAirAttack;
import com.github.kkysen.megamashbros.actions.DownTiltAttack;
import com.github.kkysen.megamashbros.actions.Executable;
import com.github.kkysen.megamashbros.actions.ForwardAirAttack;
import com.github.kkysen.megamashbros.actions.ForwardTiltAttack;
import com.github.kkysen.megamashbros.actions.Jump;
import com.github.kkysen.megamashbros.actions.Message;
import com.github.kkysen.megamashbros.actions.MoveLeft;
import com.github.kkysen.megamashbros.actions.MoveRight;
import com.github.kkysen.megamashbros.actions.RangeAttack;
import com.github.kkysen.megamashbros.actions.Stop;
import com.github.kkysen.megamashbros.actions.UpAirAttack;
import com.github.kkysen.megamashbros.actions.UpTiltAttack;
import com.github.kkysen.megamashbros.ai.FrozenAI;
import com.github.kkysen.megamashbros.ai.JumpingAI;
import com.github.kkysen.megamashbros.ai.RandomAI;
import com.github.kkysen.megamashbros.ai.SmartAI;
import com.github.kkysen.megamashbros.core.Player;
import com.github.kkysen.megamashbros.core.State;

/**
 * 
 * 
 * @author Khyber Sen
 */
public class Mario extends Player {
    
    private static final String NAME = Mario.class.getSimpleName();
    private static final Texture SPRITES = new Texture(asset("sprites_transparent.png"));
    
    public static void dispose() {
        SPRITES.dispose();
    }
    
    private static State newState(final String name, final Animation<TextureRegion> animation) {
        return new State(NAME + "'s " + name, animation);
    }
    
    private static State newState(final String name, final float frameDuration,
            final Array<TextureRegion> textureRegions, final PlayMode playMode) {
        return newState(name, new Animation<>(frameDuration, textureRegions, playMode));
    }
    
    private static State newState(final String name, final float frameDuration,
            final Array<TextureRegion> textureRegions) {
        return newState(name, frameDuration, textureRegions, PlayMode.NORMAL);
    }
    
    private static final State idleRight = newState("idleRight", 0.1f,
            Textures.getFrames(
                    SPRITES,
                    6,
                    16, 24, 27, 38),
            PlayMode.LOOP_PINGPONG);
    
    private static Array<TextureRegion> temp;
    static {
        temp = Textures.getFrames(
                SPRITES,
                6,
                16, 24, 27, 38);
        temp.forEach((x) -> x.flip(true, false));
    }
    
    private static final State idleLeft = newState("idleLeft", 0.2f, temp, PlayMode.LOOP_PINGPONG);
    
    private static final State moveRightState = newState("moveRight", 0.1f,
            Textures.getFrames(
                    SPRITES,
                    8,
                    10, 147, 32, 38),
            PlayMode.LOOP);
    
    //I know this is dirty
    static {
        temp = Textures.getFrames(
                SPRITES,
                8,
                10, 147, 32, 38);
        temp.forEach((x) -> x.flip(true, false));
    }
    
    private static final State moveLeftState = newState("moveLeft", 0.1f, temp, PlayMode.LOOP);
    
    private static final State jumpState = newState("jump", 0.5f,
            Textures.getFrames(
                    SPRITES,
                    17, 84,
                    new int[][] {
                        {28, 42},
                        {31, 42},
                        {33, 44}
                    }));
    
    private static final State forwardTiltState = newState("forward tilt", 0.1f,
            Textures.getFrames(
                    SPRITES,
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
                    }));
    
    private static final State downTiltState = newState("down tilt", 0.1f,
            Textures.getFrames(
                    SPRITES,
                    12, 788,
                    new int[][] {
                        {29, 36},
                        {56, 36},
                        {39, 36},
                        {43, 36},
                        {33, 36},
                        {34, 36}
                    }));
    
    static {
        temp = Textures.getFrames(
                SPRITES,
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
        
        final Array<TextureRegion> temp2 = Textures.getFrames(
                SPRITES,
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
    
    private static final State forwardAirState = newState("forward air", 0.1f, temp);
    
    private static final State upTiltState = newState("up tilt", 0.1f,
            Textures.getFrames(
                    SPRITES,
                    11, 535,
                    new int[][] {
                        {40, 53},
                        {41, 53},
                        {30, 53},
                        {29, 53},
                        {29, 53},
                        {28, 53}
                    }));
    
    private static final State upAirState = newState("up air", 0.1f,
            Textures.getFrames(
                    SPRITES,
                    11, 612,
                    new int[][] {
                        {43, 77},
                        {56, 77},
                        {43, 77},
                        {44, 77},
                        {44, 77},
                        {43, 77},
                        {29, 77},
                        {35, 77}
                    }));
    
    private static final State downAirState = newState("up air state", 0.1f,
            Textures.getFrames(
                    SPRITES,
                    10, 853,
                    new int[][] {
                        {37, 40},
                        {29, 40},
                        {32, 40},
                        {35, 40},
                        {33, 40},
                        {34, 40},
                        {36, 40}
                    }));
    
    public static Mario userControlled() {
        return new Mario(User.get());
    }
    
    public static Mario randomlyControlled() {
        return new Mario(new RandomAI());
    }
    
    public static Mario frozen() {
        return new Mario(new FrozenAI());
    }
    
    public static Mario jumping() {
        return new Mario(new JumpingAI());
    }
    
    public static Mario smart() {
        return new Mario(new SmartAI());
    }
    
    public Mario(final Controller controller) {
        super(NAME, controller, idleRight, 1, new Executable[] {
            new Stop(idleRight),
            new MoveLeft(moveRightState, 0f, 200f),
            new MoveRight(moveRightState, 0f, 200f),
            new Jump(jumpState, 1f, 0.1f, 500f),
            new RangeAttack(idleRight, 0, 1f, 1f, 3f, 3f),
            new ForwardTiltAttack(forwardTiltState, 0.1f, 0.1f, .5f, 5f, 5f),
            new DownTiltAttack(downTiltState, 0.1f, 0.1f, 0.3f, 3f, 3f),
            new ForwardAirAttack(forwardAirState, 0.6f, 0.1f, .8f, 3f, 6f),
            new UpTiltAttack(upTiltState, .15f, .3f, .1f, 1f, 10f),
            new Message(KeyBinding.PRINT, player -> player),
            new Message(KeyBinding.PRINT_STATE, player -> player.state),
            new UpAirAttack(upAirState, .15f, .25f, .05f, 1.5f, 8f),
            new DownAirAttack(downAirState, .05f, .2f, .2f, 1.5f, 6f),
        });
    }
    
}
