package com.github.kkysen.libgdx.util;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
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
            regions.add(new TextureRegion(texture, x + (width) * i, y, width, height));
        }
        return regions;
    }
    
    /**
     * Use this version when widths are not uniform across frames
     * Precondition: sizes.size == numFrames
     * */
    public static Array<TextureRegion> getFrames(final Texture texture, final int numFrames,
            final int x, final int y, final Vector2... sizes) {
        final Array<TextureRegion> regions = new Array<>();
        int curOffset = 0;
        
        for (int i = 0; i < numFrames; i++) {
        	System.out.println("Adding frame " + i);
            regions.add(new TextureRegion(texture, x + curOffset, y,
            		(int)sizes[i].x, (int)sizes[i].y));
            System.out.println(curOffset + " " + sizes[i].x);
            System.out.println(regions.get(i));
            
            curOffset += sizes[i].x;
        }
        
        return regions;
    }
    
}
