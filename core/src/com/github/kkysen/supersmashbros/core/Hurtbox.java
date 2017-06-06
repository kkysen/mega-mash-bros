package com.github.kkysen.supersmashbros.core;

import com.badlogic.gdx.graphics.Color;
import com.github.kkysen.supersmashbros.actions.Attack;

/**
 * 
 * 
 * @author Khyber Sen
 */
public class Hurtbox extends Box {
    
    private static final float DAMAGE_MULTIPLIER = 0.1f; // FIXME
    
    public Hurtbox(final Player player, final float width, final float height,
            final float lifetime, final float warmupTime) {
        super(player, width, height, lifetime, warmupTime);
    }
    
    public Hurtbox(final Player player) {
        this(player, player.normalWidth(), player.normalHeight(), Float.MAX_VALUE, 0);
    }
    
    @Override
    protected Color getColor() {
        return player.isAI() ? Color.BLUE : Color.GOLD;
    }
    
    @Override
    public String toString() {
        return player + "'s Hurtbox";
    }
    
    public float damageTakenBy(final Hitbox hitbox) {
    	
    	float intersect = intersectionArea(hitbox);
    	/*float areaMult = (float) ((hitbox.bounds.area()-this.bounds.area()) +
    			((hitbox.bounds.area()*Math.E)/this.bounds.area()));
    			(intersect*intersect)/(float)(Math.E * intersect*2);
    	float fin = hitbox.attack.damage * DAMAGE_MULTIPLIER;*/
    	//float res = (hitbox.attack.damage * (intersect / hitbox.bounds.area() ));
    	
    	/*System.out.println(intersectionArea(hitbox) + ", " +
    			((hitbox.bounds.area()-this.bounds.area()) +
    			(hitbox.bounds.area()/this.bounds.area())));
    	
    	System.out.println((intersectionArea(hitbox)/
    			(hitbox.bounds.area()-this.bounds.area()) +
    			(hitbox.bounds.area()/this.bounds.area()))
    		* hitbox.attack.damage * DAMAGE_MULTIPLIER);*/
    	
    	//System.out.println(hitbox.bounds.area() + ", " + intersect + ", " + areaMult + ", " + fin + ", " + hitbox.attack.damage);
    	//System.out.println(res);
    	//System.out.println("exp: " + (hitbox.attack.damage * (intersect / hitbox.bounds.area() )) );
    	
    	/*System.out.println((hitbox.bounds.area()-this.bounds.area()) +
        			(hitbox.bounds.area()/this.bounds.area()));*/
    	
    	if (intersect == 0) return 0;
        
    	//return res;
    	return intersect;
    	/*return 	(intersectionArea(hitbox)/
        			(hitbox.bounds.area()-this.bounds.area()) +
        			(hitbox.bounds.area()/this.bounds.area()))
        		* hitbox.attack.damage * DAMAGE_MULTIPLIER;*/
    }
    
    public float collide(final Hitbox hitbox) {
        if (isWarmingUp() || hitbox.isWarmingUp()) {
            return 0;
        }
        log(this + " collided with " + hitbox);
        return damageTakenBy(hitbox);
    }
    
    /**
     * Damage dealt, simplified:
     * Base dmg (from action) * contact area with hurtbox
     */
    public float damageTakenBy(final Attack attack, final Hitbox hitbox) {
        return intersectionArea(hitbox) * attack.damage/* * DAMAGE_MULTIPLIER*/;
    }
    
    public float collide(final Attack attack, final Hitbox hitbox) {
        final float damage = damageTakenBy(attack, hitbox);
        //hitbox.player.attacked(damage);
        return damage;
    }
    
    @Override
    public boolean subUpdate() {
        bounds.x = player.position.x;
        bounds.y = player.position.y;
        return true;
    }
    
}
