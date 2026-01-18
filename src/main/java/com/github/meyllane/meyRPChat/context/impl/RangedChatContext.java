package com.github.meyllane.meyRPChat.context.impl;

import com.github.meyllane.meyRPChat.context.ChatContext;
import com.github.meyllane.meyRPChat.context.ext.Range;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class RangedChatContext extends ChatContext {
    private final Range range;
    private String itemReplacementWord = "";
    private ItemStack heldItem = null;

    public RangedChatContext(Player sender, Component message, Range range) {
        super(sender, message);
        this.range = range;
    }

    public Range getRange() {
        return range;
    }

    public String getItemReplacementWord() {
        return itemReplacementWord;
    }

    public ItemStack getHeldItem() {
        return this.heldItem;
    }

    public void setItemRplacementWord(String word) {
        this.itemReplacementWord = word;
    }

    public void setHeldItem(ItemStack item) {
        this.heldItem = item;
    }
}
