package com.github.meyllane.meyRPChat.channel.impl;

import com.github.meyllane.meyRPChat.MeyRPChat;
import com.github.meyllane.meyRPChat.channel.BaseChannel;
import com.github.meyllane.meyRPChat.channel.ChannelOption;
import com.github.meyllane.meyRPChat.context.impl.BaseChatContext;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.stream.Stream;

public class WorldChannel extends BaseChannel<BaseChatContext> {
    public WorldChannel(String format, ChannelOption options) {
        super(format, options);
    }

    @Override
    public void send(BaseChatContext ctx) {
        if (this.hasOptionSet("sendPermission") && !this.hasPermission(ctx.getSender(), "sendPermission")) throw new RuntimeException(
                MeyRPChat.sendDeniedMessage
        );

        Component message = this.build(ctx);
        Stream<Player> targets = ctx.getSender().getWorld().getPlayers().stream();

        if (this.hasOptionSet("receivePermission")) {
            targets = targets.filter(target -> this.hasPermission(target, "receivePermission"));
        }

        Audience.audience(targets.toList()).sendMessage(message);
    }

    @Override
    protected Component build(BaseChatContext ctx) {
        return MeyRPChat.mm.deserialize(this.format, MeyRPChat.tagProviderRegistry.build(ctx));
    }
}
