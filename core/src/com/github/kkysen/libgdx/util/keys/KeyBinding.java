package com.github.kkysen.libgdx.util.keys;

import com.badlogic.gdx.utils.ByteArray;

/**
 * 
 * 
 * @author Khyber Sen
 */
public enum KeyBinding {
    
    LEFT(Key.A),
    RIGHT(Key.D),
    JUMP(Key.W),
    MAIN_ATTACK(Key.SPACE),
    ;
    
    private final Key key;
    final byte[] keys;
    
    private KeyBinding(final Key... keys) {
        if (keys.length == 0) {
            throw new IllegalArgumentException();
        }
        if (keys.length == 1) {
            key = keys[0];
            this.keys = null;
        } else {
            key = null;
            this.keys = new byte[keys.length];
            for (int i = 0; i < keys.length; i++) {
                this.keys[i] = (byte) keys[i].keyCode;
            }
        }
    }
    
    public boolean isPressed(final KeyInput input) {
        if (key != null) {
            return input.isPressed(key);
        }
        final ByteArray pressedKeys = input.getKeysPressed();
        if (keys.length > pressedKeys.size) {
            return false;
        }
        for (int i = 0; i < keys.length; i++) {
            if (keys[i] != pressedKeys.get(i)) {
                return false;
            }
        }
        return true;
    }
    
    private static final KeyBinding[] VALUES = values();
    
    public static KeyBinding get(final int ordinal) {
        return VALUES[ordinal];
    }
    
}
