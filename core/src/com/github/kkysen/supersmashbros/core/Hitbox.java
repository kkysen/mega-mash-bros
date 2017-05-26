package com.github.kkysen.supersmashbros.core;

import lombok.RequiredArgsConstructor;

/**
 * 
 * 
 * @author Khyber Sen
 */
@RequiredArgsConstructor
public class Hitbox extends Box {
    
    public final Player player;
    
    public final float damage;
    
    @Override
    public boolean update() {
        return true; // TODO
    }
    
}
