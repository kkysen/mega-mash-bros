package com.github.kkysen.libgdx.util.keys;

import com.badlogic.gdx.utils.ByteArray;

/**
 * 
 * 
 * @author Khyber Sen
 */
public class KeyInput {
    
    private static final int NUM_KEYS = 256;
    
    private final boolean[] pressedKeysSet = new boolean[NUM_KEYS];
    private final ByteArray pressedKeys = new ByteArray(4);
    
    protected KeyInput() {}
    
    public void pressKey(final int keyCode) {
        pressedKeysSet[keyCode] = true;
        pressedKeys.add((byte) keyCode);
    }
    
    public void releaseKey(final int keyCode) {
        pressedKeysSet[keyCode] = false;
        pressedKeys.removeValue((byte) keyCode);
    }
    
    public void pressKeys(final KeyBinding keyBinding) {
        for (final byte key : keyBinding.keys) {
            pressKey(key + 128);
        }
    }
    
    public void releaseKeys(final KeyBinding keyBinding) {
        for (final byte key : keyBinding.keys) {
            releaseKey(key + 128);
        }
    }
    
    public boolean isPressed(final Key... keys) {
        for (final Key key : keys) {
            if (!pressedKeysSet[key.keyCode]) {
                return false;
            }
        }
        return true;
    }
    
    public ByteArray getKeysPressed() {
        return pressedKeys;
    }
    
}
