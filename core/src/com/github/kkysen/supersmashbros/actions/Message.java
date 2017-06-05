package com.github.kkysen.supersmashbros.actions;

import java.util.function.Function;

import com.github.kkysen.libgdx.util.keys.KeyBinding;
import com.github.kkysen.supersmashbros.core.Player;
import com.github.kkysen.supersmashbros.core.State;

/**
 * 
 * 
 * @author Khyber Sen
 */
public class Message extends Executable {
    
    private final Function<Player, ?> messenger;
    
    public Message(final KeyBinding keyBinding, final Function<Player, ?> messenger) {
        super(keyBinding);
        this.messenger = messenger;
    }
    
    @Override
    public State execute(final Player player) {
        System.out.println(player + ": " + messenger.apply(player));
        return player.state;
    }
    
}
