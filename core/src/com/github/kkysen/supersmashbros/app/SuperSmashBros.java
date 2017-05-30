package com.github.kkysen.supersmashbros.app;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.github.kkysen.supersmashbros.core.World;
import com.github.kkysen.supersmashbros.players.Mario;

public class SuperSmashBros extends ApplicationAdapter {
    
    public static final String TITLE = "Super Smash Bros";
    private static final Path ASSETS = Paths.get("").toAbsolutePath().getParent()
            .resolve("core/assets");
    
    public static FileHandle open(final Path path) {
        return Gdx.files.internal(path.toString());
    }
    
    public static FileHandle openAsset(final String path) {
        return open(ASSETS.resolve(path));
    }
    
    private OrthographicCamera camera;
    private SpriteBatch batch;
    
    private World world;
    
    private World createWorld() {
        System.out.println(ASSETS.toAbsolutePath());
        final Texture background = new Texture(open(ASSETS.resolve("background.jpg")));
        final Sprite platform = new Sprite(new Texture(open(ASSETS.resolve("platform.png"))));
        return new World(background, platform, Mario.userControlled());
    }
    
    @Override
    public void create() {
        camera = new OrthographicCamera();
        batch = new SpriteBatch();
        world = createWorld();
        batch.begin();
        world.renderStatic(batch);
        batch.end();
    }
    
    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        world.render(batch);
        batch.end();
        pause();
        try {
            System.in.read();
        } catch (final IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        resume();
    }
    
    @Override
    public void dispose() {
        batch.dispose();
        world.dispose();
    }
    
}
