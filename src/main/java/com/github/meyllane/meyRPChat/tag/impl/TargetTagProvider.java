package com.github.meyllane.meyRPChat.tag.impl;

import com.github.meyllane.meyRPChat.context.ChatContext;
import com.github.meyllane.meyRPChat.context.impl.TargetedChatContext;
import com.github.meyllane.meyRPChat.tag.TagProvider;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

public class TargetTagProvider extends TagProvider {
    public TargetTagProvider(String id) {
        super(id);
    }

    @Override
    public void registerTag(TagResolver.Builder builder, ChatContext ctx) {
        if (ctx instanceof TargetedChatContext tctx) {
            builder.tag(this.id, Tag.inserting(tctx.getTarget().displayName()));
        }
    }
}
