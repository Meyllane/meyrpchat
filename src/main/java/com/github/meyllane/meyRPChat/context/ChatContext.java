package com.github.meyllane.meyRPChat.context;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

public abstract class ChatContext {
    private final Player sender;
    private Component message;

    protected ChatContext(Player sender, Component message) {
        this.sender = sender;
        this.message = message;
    }

    public Player getSender() {
        return sender;
    }

    public Component getMessage() {
        return message;
    }

    public void setMessage(Component message) {
        this.message = message;
    }
}
