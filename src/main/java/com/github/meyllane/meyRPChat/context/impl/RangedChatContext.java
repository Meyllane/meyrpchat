package com.github.meyllane.meyRPChat.context.impl;

import com.github.meyllane.meyRPChat.context.ChatContext;
import com.github.meyllane.meyRPChat.context.ext.Range;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

public class RangedChatContext extends ChatContext {
    private final Range range;

    public RangedChatContext(Player sender, Component message, Range range) {
        super(sender, message);
        this.range = range;
    }

    public Range getRange() {
        return range;
    }
}
