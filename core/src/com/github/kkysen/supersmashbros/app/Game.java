package com.github.kkysen.supersmashbros.app;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.github.kkysen.supersmashbros.core.Player;
import com.github.kkysen.supersmashbros.core.World;
import com.github.kkysen.supersmashbros.players.Mario;

public class Game extends ApplicationAdapter {
    
    public static final String TITLE = "Super Smash Bros";
    public static final int WIDTH = 1600;
    public static final int HEIGHT = 900;
    
    private static final Path ASSETS = Paths.get("").toAbsolutePath().getParent()
            .resolve("core/assets");
    
    public static FileHandle open(final Path path) {
        return Gdx.files.internal(path.toString());
    }
    
    public static FileHandle asset(final String path) {
        return open(ASSETS.resolve(path));
    }
    
    public static Game instance;
    
    public static float deltaTime;
    public static float speed = 1;
    
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private ShapeRenderer lineRenderer;
    
    private World world;
    
    private World createWorld() {
        final Texture background = new Texture(asset("background.jpg"));
        System.out.println(background.getHeight() + ", " + background.getWidth());
        final Sprite platform = new Sprite(new Texture(asset("platform.png")));
        final int numAIs = 1;
        final Player[] players = new Player[numAIs + 1];
        players[0] = Mario.userControlled();
        for (int i = 1; i < players.length; i++) {
            //players[i] = Mario.frozen();
            //players[i] = Mario.randomlyControlled();
            players[i] = Mario.smart();
            //players[i] = (i & 1) == 1 ? Mario.randomlyControlled() : Mario.frozen();
        }
        return new World(WIDTH, HEIGHT, background, platform, players);
    }
    
    @Override
    public void create() {
        Gdx.app.setLogLevel(Application.LOG_NONE);
        instance = this;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, WIDTH, HEIGHT);
        batch = new SpriteBatch();
        lineRenderer = new ShapeRenderer();
        world = createWorld();
    }
    
    private static void readSpeed() {
        final Scanner in;
        try {
            in = new Scanner(ASSETS.resolve("speed.txt"));
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
        if (in.hasNextLine() && in.hasNextFloat()) {
            final float newSpeed = in.nextFloat();
            if (newSpeed != speed) {
                System.out.println("\tread speed = " + newSpeed);
            }
            speed = newSpeed;
        }
        in.close();
    }
    
    @Override
    public void render() {
        readSpeed();
        deltaTime = Gdx.graphics.getDeltaTime() * speed;
        
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        world.render(batch);
        batch.end();
        
        Gdx.gl20.glLineWidth(5);
        lineRenderer.setProjectionMatrix(camera.combined);
        lineRenderer.begin(ShapeType.Line);
        world.render(lineRenderer);
        lineRenderer.end();
        
        if (world.gameOver) {
            pause();
        }
    }
    
    @Override
    public void dispose() {
        batch.dispose();
        world.dispose();
    }
    
}