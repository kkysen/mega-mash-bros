package com.github.kkysen.libgdx.util.keys;

/**
 * 
 * 
 * @author Khyber Sen
 */
public class Controller {
    
    private static final int NUM_KEYS = 256;
    
    private final boolean[] pressedKeys = new boolean[NUM_KEYS];
    
    protected Controller() {}
    
    public void pressKey(final int keyCode) {
        pressedKeys[keyCode] = true;
    }
    
    public void releaseKey(final int keyCode) {
        pressedKeys[keyCode] = false;
    }
    
    public void pressKeys(final KeyBinding keyBinding) {
        for (final Key key : keyBinding.keys) {
            pressKey(key.keyCode);
        }
    }
    
    public void releaseKeys(final KeyBinding keyBinding) {
        for (final Key key : keyBinding.keys) {
            releaseKey(key.keyCode);
        }
    }
    
    public boolean isPressed(final Key... keys) {
        for (final Key key : keys) {
            if (!pressedKeys[key.keyCode]) {
                return false;
            }
        }
        return true;
    }
    
}
