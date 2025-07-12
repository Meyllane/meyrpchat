package com.github.meyllane.meyRPChat.tag;

import com.github.meyllane.meyRPChat.context.ChatContext;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

public abstract class TagProvider {
    protected String id;

    public TagProvider(String id) {
        this.id = id;
    }

    public abstract void registerTag(TagResolver.Builder builder, ChatContext ctx);
}
