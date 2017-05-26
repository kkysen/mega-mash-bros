package com.github.kkysen.supersmashbros.app;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.github.kkysen.supersmashbros.core.World;

public class SuperSmashBros extends ApplicationAdapter {
    
    public static final String TITLE = "Super Smash Bros";
    
    private OrthographicCamera camera;
    private SpriteBatch batch;
    
    private World world;
    
    @Override
    public void create() {
        camera = new OrthographicCamera();
        batch = new SpriteBatch();
    }
    
    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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
