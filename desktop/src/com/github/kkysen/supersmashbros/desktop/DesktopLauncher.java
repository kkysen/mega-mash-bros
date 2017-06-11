package com.github.kkysen.supersmashbros.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.github.kkysen.supersmashbros.app.Game;

public class DesktopLauncher {
    
    public static void main(final String[] arg) {
        final LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = Game.TITLE;
        config.width = Game.WIDTH;
        config.height = Game.HEIGHT;
        config.resizable = true;
        //config.foregroundFPS = 6;
        new LwjglApplication(new Game(), config);
    }
    
}
