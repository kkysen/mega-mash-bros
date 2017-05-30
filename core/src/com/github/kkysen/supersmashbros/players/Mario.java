package com.github.kkysen.supersmashbros.players;

import static com.github.kkysen.supersmashbros.app.SuperSmashBros.asset;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.github.kkysen.libgdx.util.keys.KeyInput;
import com.github.kkysen.libgdx.util.keys.UserKeyInput;
import com.github.kkysen.supersmashbros.actions.Action;
import com.github.kkysen.supersmashbros.actions.Jump;
import com.github.kkysen.supersmashbros.actions.MoveLeft;
import com.github.kkysen.supersmashbros.actions.MoveRight;
import com.github.kkysen.supersmashbros.actions.SmashAttack;
import com.github.kkysen.supersmashbros.core.Player;
import com.github.kkysen.supersmashbros.core.State;

/**
 * 
 * 
 * @author Khyber Sen
 */
public class Mario extends Player {
    
    private static final State state = new State(new Animation<>(0.1f,
            new Texture(asset("mario.png"))));
    
    public static Mario userControlled() {
        return new Mario(UserKeyInput.get());
    }
    
    public Mario(final KeyInput input) {
        super("Mario", input, state, new Action[] {
            new MoveLeft(state, 1f, 10f),
            new MoveRight(state, 1f, 10f),
            new Jump(state, 1f, 1f, 50f),
            new SmashAttack(state, 2f, 3f, 5f, 5f),
        });
    }
    
}
