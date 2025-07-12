package com.github.meyllane.meyRPChat.tag.impl;

import com.github.meyllane.meyRPChat.context.ChatContext;
import com.github.meyllane.meyRPChat.context.impl.RangedChatContext;
import com.github.meyllane.meyRPChat.tag.TagProvider;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

public class RangePrefixTagProvider extends TagProvider {
    public RangePrefixTagProvider(String id) {
        super(id);
    }

    @Override
    public void registerTag(TagResolver.Builder builder, ChatContext ctx) {
        if (ctx instanceof RangedChatContext rctx) {
            builder.tag(this.id, Tag.inserting(Component.text(rctx.getRange().prefix())));
        }
    }
}
