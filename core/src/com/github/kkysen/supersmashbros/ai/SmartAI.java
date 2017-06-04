package com.github.kkysen.supersmashbros.ai;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pools;
import com.github.kkysen.libgdx.util.ExtensionMethods;
import com.github.kkysen.supersmashbros.core.Hitbox;
import com.github.kkysen.supersmashbros.core.Player;

import lombok.experimental.ExtensionMethod;

/**
 * 
 * 
 * @author Khyber Sen
 */
@ExtensionMethod(ExtensionMethods.class)
public class SmartAI extends AI {
    
    private static final int cycles = 32; // must be power of 2
    
    private int i = 0;
    
    private void evade(final Player self, final Array<Player> enemies) {
        final Vector2 dd = Pools.obtain(Vector2.class); // change in distance
        for (final Player enemy : enemies) {
            for (final Hitbox hitbox : enemy.hitboxes) {
                // assuming const acceleration
                final float distance2 = self.position.dst(hitbox.position);
                dd.distanceTraveled(hitbox.acceleration, hitbox.velocity, cycles);
                if (dd.len2() > distance2) {
                    return; // definitely too far away
                }
                hitbox.position.cpy().add(dd).dst2(self.position);
            }
        }
    }
    
    private void target(final Player self, final Array<Player> enemies) {
        for (final Player enemy : enemies) {
            
        }
    }
    
    @Override
    public void makeDecisions(final Player self, final Array<Player> enemies) {
        if ((++i & cycles) != 0) {
            return; // only run every 32 game loops
        }
        evade(self, enemies);
        target(self, enemies);
    }
    
}
