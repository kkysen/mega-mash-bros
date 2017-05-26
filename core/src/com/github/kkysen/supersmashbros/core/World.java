package com.github.kkysen.supersmashbros.core;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.github.kkysen.libgdx.util.Renderable;

import lombok.RequiredArgsConstructor;

/**
 * 
 * 
 * @author Khyber Sen
 */
@RequiredArgsConstructor
public class World implements Renderable, Disposable {
    
    private final Array<Player> players;
    
    private boolean renderedStatics = false;
    private final Texture background;
    private final Platform platform;
    
    public World(final Texture background, final Sprite platform, final Player... players) {
        this.background = background;
        this.platform = new Platform(platform);
        this.players = new Array<>(players);
    }
    
    @Override
    public void render(final Batch batch) {
        if (!renderedStatics) {
            batch.draw(background, 0, 0);
            platform.render(batch);
            renderedStatics = true;
        }
        for (int i = 0; i < players.size; i++) {
            final Player player = players.removeIndex(i);
            player.update(players);
            if (player.isAlive()) {
                players.add(player);
                players.swap(i, players.size - 1);
            } else {
                // already removed from array
                player.kill();
            }
            player.render(batch);
        }
    }
    
    @Override
    public void dispose() {
        background.dispose();
    }
    
}
