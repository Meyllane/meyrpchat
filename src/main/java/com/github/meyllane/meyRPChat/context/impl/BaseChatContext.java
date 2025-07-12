package com.github.meyllane.meyRPChat.context.impl;

import com.github.meyllane.meyRPChat.context.ChatContext;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

public class BaseChatContext extends ChatContext {
    public BaseChatContext(Player sender, Component message) {
        super(sender, message);
    }
}
