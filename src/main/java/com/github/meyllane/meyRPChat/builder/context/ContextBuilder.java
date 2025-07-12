package com.github.meyllane.meyRPChat.builder.context;

import com.github.meyllane.meyRPChat.context.ChatContext;
import org.bukkit.entity.Player;

public interface ContextBuilder<C extends ChatContext>{
    C build(Player sender, String message);
}
