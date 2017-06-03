package com.github.kkysen.libgdx.util.keys;

import java.util.concurrent.ThreadLocalRandom;

/**
 * 
 * 
 * @author Khyber Sen
 */
public enum KeyBinding {
    
    STOP(Key.S),
    LEFT(Key.A),
    RIGHT(Key.D),
    JUMP(Key.W),
    MAIN_ATTACK(Key.SPACE),
    ;
    
    final Key[] keys;
    
    private KeyBinding(final Key... keys) {
        this.keys = keys;
    }
    
    public boolean isPressed(final Controller controller) {
        for (final Key key : keys) {
            if (!controller.isPressed(key)) {
                return false;
            }
        }
        return true;
    }
    
    private static final KeyBinding[] VALUES = values();
    public static final int COUNT = VALUES.length;
    
    public static KeyBinding get(final int ordinal) {
        return VALUES[ordinal];
    }
    
    public static KeyBinding random() {
        return VALUES[ThreadLocalRandom.current().nextInt(COUNT)];
    }
    
}
