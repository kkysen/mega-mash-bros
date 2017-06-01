package com.github.kkysen.libgdx.util;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

/**
 * 
 * 
 * @author Khyber Sen
 */
public class Textures {
    
    private Textures() {}
    
    public static Array<TextureRegion> getFrames(final Texture texture, final int numFrames,
            final int x, final int y, final int width, final int height) {
        final Array<TextureRegion> regions = new Array<>();
        for (int i = 0; i < numFrames; i++) {
            regions.add(new TextureRegion(texture, x + width * i, y, width, height));
        }
        return regions;
    }
    
}
