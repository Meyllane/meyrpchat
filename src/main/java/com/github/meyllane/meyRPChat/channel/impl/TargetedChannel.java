package com.github.meyllane.meyRPChat.channel.impl;

import com.github.meyllane.meyRPChat.MeyRPChat;
import com.github.meyllane.meyRPChat.channel.BaseChannel;
import com.github.meyllane.meyRPChat.channel.ChannelOption;
import com.github.meyllane.meyRPChat.context.impl.TargetedChatContext;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;

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

        Audience.audience(ctx.getTarget()).sendMessage(message);
    }

    @Override
    protected Component build(TargetedChatContext ctx) {
        return MeyRPChat.mm.deserialize(this.format, MeyRPChat.tagProviderRegistry.build(ctx));
    }
}
