package com.github.kkysen.libgdx.util.keys;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;

/**
 * 
 * 
 * @author Khyber Sen
 */
public class User extends Controller implements InputProcessor {
    
    private static final User INSTANCE = new User();
    static {
        Gdx.input.setInputProcessor(INSTANCE);
    }
    
    public static User get() {
        return INSTANCE;
    }
    
    private User() {}
    
    @Override
    public boolean keyDown(final int keyCode) {
        System.out.println(Key.get(keyCode) + " pressed");
        pressKey(keyCode);
        return true;
    }
    
    @Override
    public boolean keyUp(final int keyCode) {
        System.out.println(Key.get(keyCode) + " released");
        releaseKey(keyCode);
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
