package com.github.kkysen.supersmashbros.ai;

import java.util.Comparator;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pools;
import com.github.kkysen.libgdx.util.ExtensionMethods;
import com.github.kkysen.libgdx.util.keys.KeyBinding;
import com.github.kkysen.supersmashbros.app.Game;
import com.github.kkysen.supersmashbros.core.Hitbox;
import com.github.kkysen.supersmashbros.core.Platform.Relation;
import com.github.kkysen.supersmashbros.core.Player;

import lombok.experimental.ExtensionMethod;

/**
 * 
 * 
 * @author Khyber Sen
 */
@ExtensionMethod(ExtensionMethods.class)
public class SmartAI extends AI {
    
    private static final int cycles = 4; // must be power of 2
    private static final float radius2 = cycles * cycles * 50f;
    
    private static final Vector2 dd = Pools.obtain(Vector2.class); // change in distance
    private static final Vector2 xyf = Pools.obtain(Vector2.class); // final position
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
    
    private boolean evade(final Player self, final Array<Player> enemies, final float dt) {
        // TODO maybe I should sort all the hitboxes first to evade the closer ones first
        for (final Player enemy : enemies) {
            for (final Hitbox hitbox : enemy.hitboxes) {
                dd.distanceTraveled(hitbox.acceleration, hitbox.velocity, dt);
                if (xyf.set(hitbox.position).add(dd).dst2(self.position) < radius2) {
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
    
    private boolean target(final Player self, final Array<Player> enemies) {
        final Vector2 position = self.position;
        final float[] distances = new float[Player.numPlayers];
        for (final Player enemy : enemies) {
            distances[enemy.id] = position.dst(enemy.position);
        }
        final Comparator<Player> cmpByDistance = //
                (a, b) -> (int) (distances[a.id] - distances[b.id]);
        final Player[] enemiesArray = enemies.toArray().sorted(cmpByDistance);
        for (final Player enemy : enemiesArray) {
            // FIXME implement logic
            if (distances[enemy.id] > cycles * 2) {
                continue;
            }
            pressKeys(KeyBinding.MAIN_ATTACK);
            break;
        }
        final float x = position.x;
        final Relation platformRelation = self.world.platform.xRelation(x);
        switch (platformRelation) {
            case MIDDLE:
                final float dx = enemiesArray[0].position.x - x;
                if (dx < 0) {
                    pressKeys(KeyBinding.LEFT);
                    break;
                } else {
                    pressKeys(KeyBinding.RIGHT);
                    break;
                }
            case LEFT_MARGIN:
                pressKeys(KeyBinding.RIGHT);
                break;
            case RIGHT_MARGIN:
                pressKeys(KeyBinding.LEFT);
                break;
            case OFF_LEFT:
                pressKeys(KeyBinding.JUMP);
                pressKeys(KeyBinding.RIGHT);
                break;
            case OFF_RIGHT:
                pressKeys(KeyBinding.JUMP);
                pressKeys(KeyBinding.LEFT);
                break;
        }
        return false;
    }
    
    @Override
    public void makeDecisions(final Player self, final Array<Player> enemies) {
        if ((++i & cycles - 1) != 0) {
            return; // only run every #cycles game loops
        }
        final float dt = cycles * Game.deltaTime; // delta time
        // using short circuit
        final boolean dummy = evade(self, enemies, dt)
                || target(self, enemies);
    }
    
}
