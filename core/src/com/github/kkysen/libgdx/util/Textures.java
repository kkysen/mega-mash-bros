package com.github.kkysen.libgdx.util;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;
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
    
    /**
     * Use this version when widths are not uniform across frames
     */
    public static Array<TextureRegion> getFrames(final Texture texture, final int x, final int y,
            final GridPoint2... sizes) {
        final Array<TextureRegion> regions = new Array<>(sizes.length);
        int curOffset = 0;
        
        for (int i = 0; i < sizes.length; i++) {
            final GridPoint2 size = sizes[i];
            System.out.println("Adding frame " + i);
            regions.add(new TextureRegion(texture, x + curOffset, y, size.x, size.y));
            System.out.println(curOffset + " " + size.x);
            System.out.println(regions.get(i));
            
            curOffset += size.x;
        }
        
        return regions;
    }
    
}
