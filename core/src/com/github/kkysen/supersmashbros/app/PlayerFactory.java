package com.github.kkysen.supersmashbros.app;

import static com.github.kkysen.supersmashbros.app.Game.asset;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.github.kkysen.libgdx.util.keys.Controller;
import com.github.kkysen.libgdx.util.keys.User;
import com.github.kkysen.supersmashbros.ai.AI;
import com.github.kkysen.supersmashbros.ai.FrozenAI;
import com.github.kkysen.supersmashbros.ai.JumpingAI;
import com.github.kkysen.supersmashbros.ai.RandomAI;
import com.github.kkysen.supersmashbros.ai.SmartAI;
import com.github.kkysen.supersmashbros.core.Player;
import com.github.kkysen.supersmashbros.players.Mario;

/**
 * 
 * 
 * @author Khyber Sen
 */
@SuppressWarnings("unchecked")
public class PlayerFactory {
    
    private static final Class<?>[] PLAYERS = {
        Mario.class,
    };
    
    private static final Class<?>[] AIs = {
        SmartAI.class,
        FrozenAI.class,
        JumpingAI.class,
        RandomAI.class,
    };
    
    private static final Map<String, Constructor<? extends Player>> playerConstructors = new HashMap<>();
    private static final Map<String, Class<? extends AI>> aiClasses = new HashMap<>();
    
    static {
        final List<Class<?>> playerClasses = new ArrayList<>(Arrays.asList(PLAYERS));
        playerClasses.removeIf(player -> Modifier.isAbstract(player.getModifiers()));
        for (final Class<?> playerClass : playerClasses) {
            final Constructor<? extends Player> playerConstructor;
            try {
                playerConstructor = (Constructor<? extends Player>) playerClass
                        .getConstructor(Controller.class);
            } catch (NoSuchMethodException | SecurityException e) {
                throw new RuntimeException(e);
            }
            playerConstructors.put(playerClass.getSimpleName(), playerConstructor);
        }
        
        final List<Class<?>> aiClassesSet = new ArrayList<>(Arrays.asList(AIs));
        aiClassesSet.removeIf(ai -> Modifier.isAbstract(ai.getModifiers()));
        for (final Class<?> aiClass : aiClassesSet) {
            aiClasses.put(aiClass.getSimpleName(), (Class<? extends AI>) aiClass);
        }
    }
    
    public static void fromJson(final Array<Player> players, final JsonValue json,
            final boolean isUser) {
        final String playerName = json.getString("character");
        final Constructor<? extends Player> playerConstructor = playerConstructors.get(playerName);
        if (playerConstructor == null) {
            throw new IllegalArgumentException(
                    playerConstructor + " is not a valid Player class, choose another player");
        }
        
        final Controller controller;
        if (isUser) {
            controller = User.get();
        } else {
            final String aiName = json.getString("controller");
            final Class<? extends AI> aiClass = aiClasses.get(aiName);
            if (aiClass == null) {
                throw new IllegalArgumentException(
                        aiName + " is not a valid AI class, choose another AI controller");
            }
            try {
                controller = aiClass.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        
        try {
            final int number = isUser ? 1 : json.getInt("number", 1);
            for (int i = 0; i < number; i++) {
                players.add(playerConstructor.newInstance(controller));
            }
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
    
    public static Player[] fromJson(final JsonValue json) {
        final Array<Player> players = new Array<>(Player.class);
        fromJson(players, json.get("self"), true);
        for (final JsonValue jsonPlayer : json.get("enemies")) {
            fromJson(players, jsonPlayer, false);
        }
        return players.toArray();
    }
    
    public static Player[] fromJson(final String fileName) {
        return fromJson(new JsonReader().parse(asset(fileName)));
    }
    
    public static Player[] fromJson() {
        return fromJson("options.json");
    }
    
    public static void main(final String[] args) {
        for (final Player player : fromJson()) {
            System.out.println(player);
        }
    }
    
}
