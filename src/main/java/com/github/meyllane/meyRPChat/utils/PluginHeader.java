package com.github.meyllane.meyRPChat.utils;

import com.github.meyllane.meyRPChat.MeyRPChat;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentApplicable;
import net.kyori.adventure.text.format.TextColor;

public class PluginHeader {
    public static Component getPluginHeader() {
        return MeyRPChat.mm.deserialize("[<gradient:#AAE2FF:#F88CFD>MeyRPChat</gradient>] ");
    }

    public static Component buildErrorComponent(RuntimeException e) {
        return PluginHeader.getPluginHeader().append(Component.text(e.getMessage()).color(TextColor.fromHexString("#FD4F4F")));
    }
}
