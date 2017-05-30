package com.github.kkysen.libgdx.util;

import com.badlogic.gdx.Gdx;

/**
 * 
 * 
 * @author Khyber Sen
 */
public interface Loggable {
    
    public default void log(final String message) {
        Gdx.app.log(getClass().getSimpleName(), message);
    }
    
    public default void error(final String message) {
        Gdx.app.error(getClass().getSimpleName(), message);
    }
    
}
