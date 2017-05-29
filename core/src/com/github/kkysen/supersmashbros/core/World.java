package com.github.kkysen.supersmashbros.core;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.github.kkysen.libgdx.util.ExtensionMethods;
import com.github.kkysen.libgdx.util.Loggable;
import com.github.kkysen.libgdx.util.Renderable;

import lombok.experimental.ExtensionMethod;

/**
 * The {@link World} class is the main class encompassing the whole game. It
 * contains a {@link #background} {@link Texture} and a single rectangular
 * {@link #platform}. It also has an {@link Array}&lt;{@link Player}&gt; for all
 * the {@link #players}, although I assume it will normally just be two
 * {@link #players}. When the {@link World} is rendered, it loops through all
 * the {@link #players}, updating them, checking if anyone won the game yet,
 * checking if anyone died by falling off, checking if they hit the
 * {@link #platform}, and then rendering the {@link #players} themselves.
 * 
 * @author Khyber Sen
 */
@ExtensionMethod(ExtensionMethods.class)
public class World implements Renderable, Disposable, Loggable {
    
    private final Array<Player> players;
    
    private boolean renderedStatics = false;
    private final Texture background;
    private final Rectangle bounds;
    private final Platform platform;
    
    public World(final Texture background, final Sprite platform, final Player... players) {
        this.background = background;
        bounds = new Rectangle(0, 0, background.getWidth(), background.getHeight());
        this.platform = new Platform(platform);
        this.players = new Array<>(players);
    }
    
    @Override
    public void render(final Batch batch) {
        if (!renderedStatics) {
            log("rendered background and platform");
            batch.draw(background, 0, 0);
            platform.render(batch);
            renderedStatics = true;
        }
        for (int i = 0; i < players.size; i++) {
            final Player player = players.removeIndex(i);
            log("updating " + player);
            player.update(players);
            if (player.hasWon()) {
                log(player + " has won");
                finishGame();
                return;
            }
            if (player.isAlive(bounds)) {
                players.add(player);
                players.swap(i, players.size - 1);
            } else {
                log(player + " has been killed");
                // already removed from array
                player.kill();
            }
            player.checkHitPlatform(platform);
            log(player + " rendered");
            player.render(batch);
        }
    }
    
    private void finishGame() {
        log("game over");
        // TODO
    }
    
    @Override
    public void dispose() {
        background.dispose();
    }
    
}
