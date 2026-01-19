package com.github.meyllane.meyRPChat.tag.impl;

import com.github.meyllane.meyRPChat.context.ChatContext;
import com.github.meyllane.meyRPChat.context.impl.RangedChatContext;
import com.github.meyllane.meyRPChat.tag.TagProvider;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.inventory.ItemStack;

public class MessageItemReplacementTagProvider extends TagProvider {
    public MessageItemReplacementTagProvider(String id) {
        super(id);
    }

    @Override
    public void registerTag(TagResolver.Builder builder, ChatContext ctx) {
        if (ctx instanceof RangedChatContext rctx) {
            if (rctx.getHeldItem() == null) return;
            Component c = Component.text(rctx.getItemReplacementWord())
                    .color(TextColor.fromHexString("#ffffff"));

            ItemStack item = rctx.getHeldItem();
            Component hover = item.getItemMeta().displayName();

            if (item.lore() != null) {
                hover = hover.appendNewline();
                for (int i = 0; i <= item.lore().size() -1; i++) {
                    hover = hover
                            .appendNewline()
                            .append(item.lore().get(i));
                }
            }

            c = c.hoverEvent(HoverEvent.showText(hover));

            builder.tag(this.id, Tag.inserting(c));
        }
    }
}
