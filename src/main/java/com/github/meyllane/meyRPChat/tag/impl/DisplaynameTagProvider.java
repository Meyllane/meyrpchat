package com.github.meyllane.meyRPChat.tag.impl;

import com.github.meyllane.meyRPChat.context.ChatContext;
import com.github.meyllane.meyRPChat.tag.TagProvider;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

public class DisplaynameTagProvider extends TagProvider {
    public DisplaynameTagProvider(String id) {
        super(id);
    }

    @Override
    public void registerTag(TagResolver.Builder builder, ChatContext ctx) {
        Component c = ctx.getSender().displayName().hoverEvent(HoverEvent.showText(
                Component.text(ctx.getSender().getName())
        ));
        builder.tag(this.id, Tag.inserting(c));
    }
}
