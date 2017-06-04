package com.github.kkysen.supersmashbros.ai;

import java.util.Comparator;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pools;
import com.github.kkysen.libgdx.util.ExtensionMethods;
import com.github.kkysen.libgdx.util.keys.KeyBinding;
import com.github.kkysen.supersmashbros.app.Game;
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
    
    private static final Vector2 dd = Pools.obtain(Vector2.class); // change in distance
    private static final Vector2 xyf = Pools.obtain(Vector2.class); // final position
    private static final Circle radius = Pools.obtain(Circle.class);
    private static final Vector2 vf = Pools.obtain(Vector2.class); // final velocity
    
    private static final KeyBinding[] sectorToKeys = {
        KeyBinding.JUMP,  // 0
        KeyBinding.LEFT,  // 1
        KeyBinding.RIGHT, // 2
        KeyBinding.RIGHT, // 3
        KeyBinding.JUMP,  // 4
        KeyBinding.RIGHT, // 5
        KeyBinding.RIGHT, // 6
        KeyBinding.LEFT,  // 7
    };
    
    private int i = 0;
    
    private boolean evade(final Player self, final Array<Player> enemies, final Circle radius,
            final float dt) {
        // TODO maybe I should sort all the hitboxes first to evade the closer ones first
        for (final Player enemy : enemies) {
            for (final Hitbox hitbox : enemy.hitboxes) {
                // assuming const acceleration
                final float distance2 = self.position.dst(hitbox.position);
                dd.distanceTraveled(hitbox.acceleration, hitbox.velocity, dt);
                if (dd.len2() > distance2) {
                    break; // definitely too far away
                }
                if (radius.contains(xyf.set(hitbox.position).add(dd))) {
                    final float angle = vf.set(hitbox.velocity).mulAdd(hitbox.acceleration, dt)
                            .angle();
                    // divide unit circle into 8 sectors 0 to 7, 0 being [-22.5, 22.5]
                    // choose move based on sector
                    final int sector = (((int) angle << 1) + 45) / 90;
                    pressKeys(sectorToKeys[sector]);
                    return true;
                }
            }
        }
        return false;
    }
    
    private boolean target(final Player self, final Array<Player> enemies, final Circle radius,
            final float dt) {
        final Vector2 position = self.position;
        final float[] distances = new float[enemies.size + 1];
        for (final Player enemy : enemies) {
            distances[enemy.id] = position.dst(enemy.position);
        }
        final Comparator<Player> cmpByDistance = //
                (a, b) -> (int) (distances[a.id] - distances[b.id]);
        for (final Player enemy : enemies.toArray().sorted(cmpByDistance)) {
            // FIXME implement logic
            pressKeys(KeyBinding.MAIN_ATTACK);
        }
        return false;
    }
    
    @Override
    public void makeDecisions(final Player self, final Array<Player> enemies) {
        if ((++i & cycles) != 0) {
            return; // only run every 32 game loops
        }
        final float dt = cycles * Game.deltaTime; // delta time
        radius.set(self.position, cycles);
        // using short circuit
        final boolean dummy = evade(self, enemies, radius, dt)
                || target(self, enemies, radius, dt);
    }
    
}
