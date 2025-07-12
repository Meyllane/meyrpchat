package com.github.meyllane.meyRPChat.tag.impl;

import com.github.meyllane.meyRPChat.context.ChatContext;
import com.github.meyllane.meyRPChat.context.impl.RangedChatContext;
import com.github.meyllane.meyRPChat.tag.TagProvider;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

public class RangeColorTagProvider extends TagProvider {
    public RangeColorTagProvider(String id) {
        super(id);
    }

    @Override
    public void registerTag(TagResolver.Builder builder, ChatContext ctx) {
        if (ctx instanceof RangedChatContext rctx) {
            builder.tag(this.id, Tag.styling(TextColor.fromHexString(rctx.getRange().color())));
        }
    }
}
