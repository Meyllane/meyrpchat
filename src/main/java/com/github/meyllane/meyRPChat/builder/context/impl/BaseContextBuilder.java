package com.github.meyllane.meyRPChat.builder.context.impl;

import com.github.meyllane.meyRPChat.builder.context.ContextBuilder;
import com.github.meyllane.meyRPChat.context.impl.BaseChatContext;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

public class BaseContextBuilder implements ContextBuilder<BaseChatContext> {
    @Override
    public BaseChatContext build(Player sender, String message) {
        return new BaseChatContext(sender, Component.text(message));
    }
}
