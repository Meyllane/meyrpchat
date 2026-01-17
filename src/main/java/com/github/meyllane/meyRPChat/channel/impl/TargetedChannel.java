package com.github.meyllane.meyRPChat.channel.impl;

import com.github.meyllane.meyRPChat.MeyRPChat;
import com.github.meyllane.meyRPChat.channel.BaseChannel;
import com.github.meyllane.meyRPChat.channel.ChannelOption;
import com.github.meyllane.meyRPChat.context.impl.TargetedChatContext;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Stream;

public class TargetedChannel extends BaseChannel<TargetedChatContext> {
    public TargetedChannel(String format, ChannelOption options) {
        super(format, options);
    }

    @Override
    public void send(TargetedChatContext ctx) {
        if (this.hasOptionSet("sendPermission") && !this.hasPermission(ctx.getSender(), "sendPermission")) throw new RuntimeException(
                MeyRPChat.sendDeniedMessage
        );

        Component message = this.build(ctx);

        if (this.hasOptionSet("receivePermission")) {
            if (!this.hasPermission(ctx.getTarget(), "receivePermission")) throw new RuntimeException(
                    "Target doesn't have permission to receive this message."
            );
        }

        Audience aud = Audience.audience(ctx.getTarget());
        if (this.hasOptionSet("castOnReceive")) {
            int castRange = this.hasOptionSet("castOnReceiveRange") ? (int) this.options.getOptionValue("castOnReceiveRange") : 3;
            Stream<? extends Player> targetsStream = ctx.getSender().getServer().getOnlinePlayers().stream()
                    .filter(player -> ctx.getTarget().getLocation().distance(player.getLocation()) <= castRange);

            if (this.hasOptionSet("receivePermission")) {
                targetsStream = targetsStream
                        .filter(player -> this.hasPermission(player, "receivePermission"));
            }

            aud = Audience.audience(targetsStream.toList());
        }

        aud.sendMessage(message);
    }

    @Override
    protected Component build(TargetedChatContext ctx) {
        return MeyRPChat.mm.deserialize(this.format, MeyRPChat.tagProviderRegistry.build(ctx));
    }
}
