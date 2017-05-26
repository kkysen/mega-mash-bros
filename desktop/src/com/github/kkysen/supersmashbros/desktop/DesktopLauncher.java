package com.github.kkysen.supersmashbros.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.github.kkysen.supersmashbros.app.SuperSmashBros;

public class DesktopLauncher {
    
    public static void main(final String[] arg) {
        final LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = SuperSmashBros.TITLE;
        new LwjglApplication(new SuperSmashBros(), config);
    }
    
}
