package com.github.meyllane.meyRPChat.builder.context.impl;

import com.github.meyllane.meyRPChat.MeyRPChat;
import com.github.meyllane.meyRPChat.builder.context.ContextBuilder;
import com.github.meyllane.meyRPChat.context.impl.TargetedChatContext;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TargetedContextBuilder implements ContextBuilder<TargetedChatContext> {
    @Override
    public TargetedChatContext build(Player sender, String message) {
        Pattern pattern = Pattern.compile("\\s.+?(?=\\s)");
        Matcher matcher = pattern.matcher(message);
        if (!matcher.find()) throw new RuntimeException("Unknown or offline player.");

        String targetName = matcher.group();
        targetName = targetName.replaceAll("\\s", "");
        Player target = MeyRPChat.getPlugin(MeyRPChat.class).getServer().getPlayer(targetName);

        if (target == null) throw new RuntimeException("Unknown or offline player.");

        message = message.replaceFirst("\\s.+?\\s", "");

        return new TargetedChatContext(sender, Component.text(message), target);
    }
}
