package com.github.meyllane.meyRPChat.builder.channeldescriptor;

import com.github.meyllane.meyRPChat.channel.ChannelDescriptor;
import com.github.meyllane.meyRPChat.channel.ChannelOption;
import com.github.meyllane.meyRPChat.context.ChatContext;

public interface ChannelDescriptorBuilder {
    ChannelDescriptor<? extends ChatContext> build(String format, ChannelOption options);
}
