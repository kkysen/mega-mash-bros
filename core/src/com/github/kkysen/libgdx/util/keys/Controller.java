package com.github.kkysen.libgdx.util.keys;

import com.github.kkysen.libgdx.util.Loggable;

/**
 * @author Khyber Sen
 */
public class Controller implements Loggable {
    
    private static final int NUM_KEYS = 256;
    
    private final boolean[] pressedKeys = new boolean[NUM_KEYS];
    
    protected Controller() {}
    
    public void pressKey(final int keyCode) {
        pressedKeys[keyCode] = true;
    }
    
    public void releaseKey(final int keyCode) {
        pressedKeys[keyCode] = false;
    }
    
    public void update() {}
    
    public void pressKeys(final KeyBinding keyBinding) {
        //        if (this instanceof AI) {
        //            System.out.println(name() + " pressed " + keyBinding);
        //        }
        for (final Key key : keyBinding.keys) {
            pressKey(key.keyCode);
        }
    }
    
    public void releaseKeys(final KeyBinding keyBinding) {
        //        if (this instanceof AI) {
        //            System.out.println(name() + " released " + keyBinding);
        //        }
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
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (int keyCode = 0; keyCode < pressedKeys.length; keyCode++) {
            if (pressedKeys[keyCode]) {
                sb.append(Key.get(keyCode));
                sb.append(',');
                sb.append(' ');
            }
        }
        if (sb.length() == 1) {
            return "[]";
        }
        sb.setCharAt(sb.length() - 2, ']');
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }
    
}
