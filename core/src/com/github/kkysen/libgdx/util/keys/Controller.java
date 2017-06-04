package com.github.kkysen.libgdx.util.keys;

import com.github.kkysen.libgdx.util.Loggable;
import com.github.kkysen.supersmashbros.ai.AI;

/**
 * 
 * 
 * @author Khyber Sen
 */
public class Controller implements Loggable {
    
    private static final int NUM_KEYS = 256;
    
    //for the first time a key is pressed, 
    private final boolean[] initPressedKeys = new boolean[NUM_KEYS];
    
    //as long as a key is still down, it will appear here
    private final boolean[] pressedKeys = new boolean[NUM_KEYS];
    
    //once a key is released, it is added here to be dealt with at some point
    private final boolean[] releasedKeys = new boolean[NUM_KEYS];
    
    protected Controller() {}
    
    public void pressKey(final int keyCode) {
    	if (!pressedKeys[keyCode]) {
    		//releasedKeys[keyCode] = false;
    		initPressedKeys[keyCode] = true;
    	}
    }
    
    public void releaseKey(final int keyCode) {
        pressedKeys[keyCode] = false;
        releasedKeys[keyCode] = true;
    }
    
    public void removeKey(final int keyCode) {
    	releasedKeys[keyCode] = false;
    }
    
    public void pressKeys(final KeyBinding keyBinding) {
        if (this instanceof AI) {
            System.out.println(name() + " pressed " + keyBinding);
        }
        for (final Key key : keyBinding.keys) {
            pressKey(key.keyCode);
        }
    }
    
    public void releaseKeys(final KeyBinding keyBinding) {
        if (this instanceof AI) {
            //System.out.println(name() + " released " + keyBinding);
        }
        for (final Key key : keyBinding.keys) {
            releaseKey(key.keyCode);
        }
    }
    
    public boolean isPressed(final Key... keys) {
        for (final Key key : keys) {
            if (!initPressedKeys[key.keyCode]) {
                return false;
            }
            else {
            	initPressedKeys[key.keyCode] = false;
            	pressedKeys[key.keyCode] = true;
            }
        }
        return true;
    }
    
    public boolean isStillPressed(final Key... keys) {
        for (final Key key : keys) {
            if (!pressedKeys[key.keyCode]) {
                return false;
            }
        }
        return true;
    }
    
    public boolean hasBeenReleased(final Key... keys) {
        for (final Key key : keys) {
            if (!releasedKeys[key.keyCode]) {
                return false;
            }
            else {
            	releasedKeys[key.keyCode] = false;
            }
        }
        return true;
    }
    
    
    public boolean noKeysPressed() {
    	for (boolean b : pressedKeys) {
    		if (b) return false;
    	}
    	for (boolean b : initPressedKeys) {
    		if (b) return false;
    	}
    	
    	return true;
    }
    
    //public 
    
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
