package com.github.kkysen.libgdx.util;

import com.badlogic.gdx.utils.ByteArray;

/**
 * 
 * 
 * @author Khyber Sen
 */
public enum KeyBinding {
    
    MAIN_ATTACK(),
    ;
    
    private final byte[] keys;
    
    private KeyBinding(final Key... keys) {
        this.keys = new byte[keys.length];
        for (int i = 0; i < keys.length; i++) {
            this.keys[i] = (byte) keys[i].keycode;
        }
    }
    
    public boolean isPressed() {
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
