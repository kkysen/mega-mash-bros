package com.github.kkysen.supersmashbros.ai;

import com.badlogic.gdx.utils.Array;
import com.github.kkysen.libgdx.util.keys.Controller;
import com.github.kkysen.libgdx.util.keys.KeyBinding;
import com.github.kkysen.supersmashbros.core.Player;

/**
 * 
 * 
 * @author Khyber Sen
 */
public abstract class AI extends Controller {
    
    Array<KeyBinding> pressedKeys = new Array<>(KeyBinding.COUNT);
    
    @Override
    public final void releaseKeys(final KeyBinding keyBinding) {}
    
    @Override
    public final void pressKeys(final KeyBinding keyBinding) {
        //System.out.println(name() + " pressed " + keyBinding);
        super.pressKeys(keyBinding);
        for (final KeyBinding pressedKey : pressedKeys) {
            super.releaseKeys(pressedKey);
        }
        pressedKeys.add(keyBinding);
    }
    
    public abstract void makeDecisions(Player self, Array<Player> enemies);
    
}
