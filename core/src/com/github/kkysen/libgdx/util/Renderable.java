package com.github.kkysen.libgdx.util;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;

/**
 * 
 * 
 * @author Khyber Sen
 */
public interface Renderable {
    
    static ShapeRenderer shapeRenderer = new ShapeRenderer();
    
    public void render(Batch batch);
    
    public static Array<TextureRegion> getFrames(Texture t, int numFrames,
			int x, int y, int w, int h) {
		Array<TextureRegion> res = new Array<TextureRegion>();
		int counter = 0;
		while (counter < numFrames) {
			res.add(new TextureRegion(t, x + w*counter, y, w, h));
			counter++;
		}
		
		return res;
	}
}
