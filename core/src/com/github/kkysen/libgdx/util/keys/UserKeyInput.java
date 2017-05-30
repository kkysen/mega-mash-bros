package com.github.kkysen.libgdx.util.keys;

import com.badlogic.gdx.InputProcessor;

/**
 * 
 * 
 * @author Khyber Sen
 */
public class UserKeyInput extends KeyInput implements InputProcessor {
    
    private static final UserKeyInput INSTANCE = new UserKeyInput();
    
    public static UserKeyInput get() {
        return INSTANCE;
    }
    
    private UserKeyInput() {}
    
    @Override
    public boolean keyDown(final int keycode) {
        pressKey(keycode);
        return true;
    }
    
    @Override
    public boolean keyUp(final int keycode) {
        releaseKey(keycode);
        return false;
    }
    
    @Override
    public boolean keyTyped(final char character) {
        return false;
    }
    
    @Override
    public boolean touchDown(final int screenX, final int screenY, final int pointer,
            final int button) {
        return false;
    }
    
    @Override
    public boolean touchUp(final int screenX, final int screenY, final int pointer,
            final int button) {
        return false;
    }
    
    @Override
    public boolean touchDragged(final int screenX, final int screenY, final int pointer) {
        return false;
    }
    
    @Override
    public boolean mouseMoved(final int screenX, final int screenY) {
        return false;
    }
    
    @Override
    public boolean scrolled(final int amount) {
        return false;
    }
    
}
