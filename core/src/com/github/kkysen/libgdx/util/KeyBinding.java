package com.github.kkysen.libgdx.util;

import com.badlogic.gdx.utils.ByteArray;

/**
 * 
 * 
 * @author Khyber Sen
 */
public enum KeyBinding {
    
    NONE(Key.ANY_KEY),
    JUMP(Key.SPACE),
    
    MAIN_ATTACK(),
    ;
    
    private final Key key;
    private final byte[] keys;
    
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
                this.keys[i] = (byte) keys[i].keycode;
            }
        }
    }
    
    public boolean isPressed() {
        if (key != null) {
            return Keys.get().isPressed(key);
        }
        final ByteArray pressedKeys = Keys.get().getKeysPressed();
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
    
}
