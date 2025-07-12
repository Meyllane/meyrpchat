package com.github.meyllane.meyRPChat.context.impl;

import com.github.meyllane.meyRPChat.context.ChatContext;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

public class TargetedChatContext extends ChatContext {
    private final Player target;

    public TargetedChatContext(Player sender, Component message, Player target) {
        super(sender, message);
        this.target = target;
    }

    public Player getTarget() {
        return target;
    }
}
