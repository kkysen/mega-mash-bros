package com.github.kkysen.supersmashbros.ai;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.github.kkysen.libgdx.util.keys.Controller;
import com.github.kkysen.libgdx.util.keys.KeyBinding;
import com.github.kkysen.supersmashbros.core.Player;

/**
 * 
 * 
 * @author Khyber Sen
 */
public abstract class AI extends Controller {
    
    @Override
    public final void releaseKeys(final KeyBinding keyBinding) {}
    
    @Override
    public final void pressKeys(final KeyBinding keyBinding) {
        //System.out.println(name() + " pressed " + keyBinding);
        super.pressKeys(keyBinding);
        Timer.post(new Task() {
            
            @Override
            public void run() {
                AI.super.releaseKeys(keyBinding);
            }
            
        });
    }
    
    public abstract void makeDecisions(Player self, Array<Player> enemies);
    
}
