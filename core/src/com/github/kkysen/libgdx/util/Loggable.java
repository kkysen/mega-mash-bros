package com.github.kkysen.libgdx.util;

import com.badlogic.gdx.Gdx;

/**
 * 
 * 
 * @author Khyber Sen
 */
public interface Loggable {
    
    public default String name() {
        return getClass().getSimpleName();
    }
    
    public default void log(final String message) {
        Gdx.app.log(name(), message);
    }
    
    public default void debug(final String message) {
        Gdx.app.debug(name(), message);
    }
    
    public default void error(final String message) {
        Gdx.app.error(name(), message);
    }
    
    public default String join(final Object... objects) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0;; i++) {
            sb.append(objects[i]);
            if (i + 1 == objects.length) {
                return sb.toString();
            }
            sb.append(',');
            sb.append(' ');
        }
    }
    
}
