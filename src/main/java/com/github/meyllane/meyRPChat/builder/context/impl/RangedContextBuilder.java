package com.github.meyllane.meyRPChat.builder.context.impl;

import com.github.meyllane.meyRPChat.MeyRPChat;
import com.github.meyllane.meyRPChat.builder.context.ContextBuilder;
import com.github.meyllane.meyRPChat.context.impl.RangedChatContext;
import com.github.meyllane.meyRPChat.context.ext.Range;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class RangedContextBuilder implements ContextBuilder<RangedChatContext> {
    private static final MeyRPChat plugin = MeyRPChat.getPlugin(MeyRPChat.class);

    @Override
    public RangedChatContext build(Player sender, String message) {
        List<String> sortedKeys = new ArrayList<>(MeyRPChat.rangeRegistry.getMap().keySet());
        sortedKeys.sort(Comparator.comparingInt(String::length).reversed());

        Range foundRange = null;
        String cleanedText = message;
        for (String key : sortedKeys) {
            if (!key.isEmpty() && message.startsWith(key)) {
                foundRange = MeyRPChat.rangeRegistry.getMap().get(key);
                cleanedText = message.substring(key.length());
                break;
            }
        }
        if (foundRange == null) {
            foundRange = MeyRPChat.defaultRange;
        }

        Component cleanedComponent = PlainTextComponentSerializer.plainText().deserialize(cleanedText);

        return new RangedChatContext(sender, cleanedComponent, foundRange);
    }
}