package com.github.meyllane.meyRPChat.channel;

import com.github.meyllane.meyRPChat.builder.context.ContextBuilder;
import com.github.meyllane.meyRPChat.context.ChatContext;

public final class ChannelDescriptor<C extends ChatContext> {
    private final BaseChannel<C> channel;
    private final ContextBuilder<C> builder;

    public ChannelDescriptor(BaseChannel<C> channel, ContextBuilder<C> builder) {
        this.channel = channel;
        this.builder = builder;
    }

    public BaseChannel<C> channel() { return channel; }
    public ContextBuilder<C> builder() { return builder; }
}