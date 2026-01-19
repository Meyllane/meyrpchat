package com.github.meyllane.meyRPChat.tag.impl;

import com.github.meyllane.meyRPChat.MeyRPChat;
import com.github.meyllane.meyRPChat.context.ChatContext;
import com.github.meyllane.meyRPChat.tag.TagProvider;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

public class MessageTagProvider extends TagProvider {
    public MessageTagProvider(String id) {
        super(id);
    }

    @Override
    public void registerTag(TagResolver.Builder builder, ChatContext ctx) {
        String plainMessage = PlainTextComponentSerializer.plainText().serialize(ctx.getMessage());
        Component message = MeyRPChat.mm.deserialize(plainMessage, MeyRPChat.messageTagProviderRegistry.build(ctx));
        builder.tag(this.id, Tag.inserting(message));
    }
}
