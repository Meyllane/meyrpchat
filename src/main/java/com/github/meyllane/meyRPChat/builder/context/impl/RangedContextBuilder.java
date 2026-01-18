package com.github.meyllane.meyRPChat.builder.context.impl;

import com.github.meyllane.meyRPChat.MeyRPChat;
import com.github.meyllane.meyRPChat.builder.context.ContextBuilder;
import com.github.meyllane.meyRPChat.context.impl.RangedChatContext;
import com.github.meyllane.meyRPChat.context.ext.Range;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        String itemReplacementWord = getItemReplacementWord(message);
        ItemStack heldItem = null;
        if (!itemReplacementWord.isEmpty() && !sender.getInventory().getItemInMainHand().isEmpty()) {
            heldItem = sender.getInventory().getItemInMainHand();
            cleanedText = processItemReplacementTag(message, itemReplacementWord);
        }
        Component cleanedComponent = PlainTextComponentSerializer.plainText().deserialize(cleanedText);

        RangedChatContext context = new RangedChatContext(sender, cleanedComponent, foundRange);
        context.setHeldItem(heldItem);
        context.setItemRplacementWord(itemReplacementWord);

        return context;
    }

    private List<String> getItemReplacementSymbols() {
        if (MeyRPChat.itemReplacementRawString == null | MeyRPChat.itemReplacementRawString.isEmpty()) return null;

        List<String> elem = Arrays.stream(MeyRPChat.itemReplacementRawString.split("item")).toList();

        if (elem.size() != 2) return null;

        return elem;
    }

    private Pattern getItemReplacementPattern() {
        List<String> symbols = getItemReplacementSymbols();

        if (symbols == null) return null;

        String patt = Pattern.quote(symbols.getFirst()) + "(.+?)" + Pattern.quote(symbols.get(1));
        return Pattern.compile(patt);
    }

    private String getItemReplacementWord(String message) {
        Pattern pattern = getItemReplacementPattern();
        if (pattern == null) return "";

        Matcher matcher = pattern.matcher(message);
        if (matcher.find()) {
            return matcher.group(1);
        }

        return "";
    }

    //The implementation is atrocious. To future self: Please don't be lazy and rework that.
    private String processItemReplacementTag(String message, String word) {
        List<String> symbols = getItemReplacementSymbols();

        String patt = Pattern.quote(symbols.getFirst()) + "(.+?)" + Pattern.quote(symbols.get(1));
        return message.replaceFirst(patt, "<itemreplacement>" + word + "</itemreplacement>");
    }
}