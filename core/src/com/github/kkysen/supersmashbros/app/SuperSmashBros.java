package com.github.kkysen.supersmashbros.app;

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
import com.github.kkysen.supersmashbros.core.Player;
import com.github.kkysen.supersmashbros.core.World;

public class SuperSmashBros extends ApplicationAdapter {
    
    public static final String TITLE = "Super Smash Bros";
    private static final Path ASSETS = Paths.get("assets");
    
    private static FileHandle open(final Path path) {
        return Gdx.files.internal(path.toString());
    }
    
    private OrthographicCamera camera;
    private SpriteBatch batch;
    
    private World world;
    
    private Player createPlayer() {
        return null; // TODO
        //return new Player(actions, states, "Player 1", 1);
    }
    
    private World createWorld() {
        final Texture background = new Texture(open(ASSETS.resolve("background.png")));
        final Sprite platform = new Sprite(new Texture(open(ASSETS.resolve("platform"))));
        final Sprite player = new Sprite(new Texture(open(ASSETS.resolve("player.png"))));
        return new World(background, platform, createPlayer());
    }
    
    @Override
    public void create() {
        camera = new OrthographicCamera();
        batch = new SpriteBatch();
        world = createWorld();
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
    }
    
    @Override
    public void dispose() {
        batch.dispose();
        world.dispose();
    }
    
}
