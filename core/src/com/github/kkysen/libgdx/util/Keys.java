package com.github.kkysen.libgdx.util;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.utils.ByteArray;

/**
 * 
 * 
 * @author Khyber Sen
 */
public class Keys extends InputAdapter {
    
    private static final Keys INSTANCE = new Keys();
    
    public static Keys get() {
        return INSTANCE;
    }
    
    private static final int NUM_KEYS = 256;
    
    private final boolean[] pressedKeysSet = new boolean[NUM_KEYS];
    private final ByteArray pressedKeys = new ByteArray(4);
    
    @Override
    public boolean keyDown(final int keycode) {
        pressedKeysSet[keycode] = true;
        pressedKeys.add((byte) keycode);
        return true;
    }
    
    @Override
    public boolean keyUp(final int keycode) {
        pressedKeysSet[keycode] = false;
        pressedKeys.removeValue((byte) keycode);
        return true;
    }
    
    public boolean isPressed(final Key... keys) {
        for (final Key key : keys) {
            if (!pressedKeysSet[key.keycode]) {
                return false;
            }
        }
        return true;
    }
    
    public ByteArray getKeysPressed() {
        return pressedKeys;
    }
    
}
