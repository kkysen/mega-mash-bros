package com.github.kkysen.libgdx.util;

import java.util.Arrays;
import java.util.Comparator;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pools;
import com.github.kkysen.supersmashbros.app.Game;

import lombok.experimental.ExtensionMethod;

/**
 * @author Khyber Sen
 */
@ExtensionMethod(ExtensionMethods.class)
public class ExtensionMethods {
    
    public static <T> T[] sorted(final T[] a, final Comparator<? super T> comparator) {
        Arrays.sort(a, comparator);
        return a;
    }
    
    public static Rectangle scale(final Rectangle rectangle, final float scalar) {
        final Vector2 center = rectangle.getCenter(Pools.obtain(Vector2.class));
        final float width = rectangle.width * scalar;
        final float height = rectangle.height * scalar;
        return Pools.obtain(Rectangle.class)//
                .set(center.x - width * 0.5f, center.y - height * 0.5f, width, height);
    }
    
    public static Rectangle setPositionAndSize(final Rectangle rectangle, final float centerX,
            final float centerY, final float width, final float height) {
        rectangle.x = centerX - width * 0.5f;
        rectangle.y = centerY - height * 0.5f;
        rectangle.width = width;
        rectangle.height = height;
        return rectangle;
    }
    
    public static Rectangle setPositionAndSize(final Rectangle rectangle, final Vector2 center,
            final Vector2 size) {
        return setPositionAndSize(rectangle, center.x, center.y, size.x, size.y);
    }
    
    public static float maxX(final Rectangle rectangle) {
        return rectangle.x + rectangle.width;
    }
    
    public static float maxY(final Rectangle rectangle) {
        return rectangle.y + rectangle.height;
    }
    
    public static Vector2 maxPosition(final Rectangle rectangle, final Vector2 position) {
        position.x = rectangle.maxX();
        position.y = rectangle.maxY();
        return position;
    }
    
    public static void accelerate(final Vector2 acceleration, final Vector2 velocity,
            final Vector2 position) {
        final float deltaTime = Game.deltaTime;
        velocity.mulAdd(acceleration, deltaTime);
        position.mulAdd(velocity, deltaTime);
    }
    
    /**
     * @param vector vector to transform
     * @param angle resultant angle in radians
     * @param length resultant length or magnitude
     */
    public static void setAngleAndLength(final Vector2 vector, final float angle,
            final float length) {
        vector.x = length * (float) Math.cos(angle);
        vector.y = length * (float) Math.sin(angle);
    }
    
    public static <T> boolean contains(final T[] a, final T value) {
        for (final T t : a) {
            if (value.equals(t)) {
                return true;
            }
        }
        return false;
    }
    
    private static float distanceTraveled(final float acceleration, final float velocity,
            final float time) {
        return time * (velocity + 0.5f * acceleration * time);
    }
    
    public static Vector2 distanceTraveled(final Vector2 distance, final Vector2 acceleration,
            final Vector2 velocity,
            final float deltaTime) {
        distance.x = distanceTraveled(acceleration.x, velocity.x, deltaTime);
        distance.y = distanceTraveled(acceleration.y, velocity.y, deltaTime);
        return distance;
    }
    
}
