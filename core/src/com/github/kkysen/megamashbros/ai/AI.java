package com.github.kkysen.megamashbros.ai;

import com.badlogic.gdx.utils.Array;
import com.github.kkysen.libgdx.util.keys.Controller;
import com.github.kkysen.libgdx.util.keys.KeyBinding;
import com.github.kkysen.megamashbros.core.Player;

/**
 * 
 * 
 * @author Khyber Sen
 */
public abstract class AI extends Controller {
    
    protected static final int cycles = 4; // must be power of 2
    
    protected int cycle = 0;
    
    private Array<KeyBinding> pressedKeys = new Array<>(4);
    private Array<KeyBinding> pendingKeyPresses = new Array<>(4);
    
    @Override
    public final void update() {
        if ((++cycle & cycles - 1) == 0) {
            for (final KeyBinding toRelease : pressedKeys) {
                super.releaseKeys(toRelease);
            }
            for (final KeyBinding toPress : pendingKeyPresses) {
                super.pressKeys(toPress);
            }
            final Array<KeyBinding> temp = pressedKeys;
            temp.clear();
            pressedKeys = pendingKeyPresses;
            pendingKeyPresses = temp;
        }
    }
    
    @Override
    public final void releaseKeys(final KeyBinding keyBinding) {}
    
    @Override
    public final void pressKeys(final KeyBinding keyBinding) {
        //System.out.println(name() + " pressed " + keyBinding);
        pendingKeyPresses.add(keyBinding);
    }
    
    // will be called before update
    public abstract void makeDecisions(Player self, Array<Player> enemies);
    
}
