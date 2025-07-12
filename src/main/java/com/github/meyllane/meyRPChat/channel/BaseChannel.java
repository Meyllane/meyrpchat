package com.github.meyllane.meyRPChat.channel;

import com.github.meyllane.meyRPChat.context.ChatContext;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.Map;

public abstract class BaseChannel<C extends ChatContext>{
    protected final String format;
    protected final ChannelOption options;
    public BaseChannel(String format, ChannelOption options) {
        this.format = format;
        this.options = options;
    }

    public abstract void send(C ctx);

    protected abstract Component build(C ctx);

    protected boolean hasPermission(Player player, String key) {
        Map<String, Object> optionsMap = options.getOptions();
        if (optionsMap.containsKey(key)) {
            Object perm = optionsMap.get(key);
            if (perm instanceof String) {
                return player.hasPermission((String) perm);
            }
        }
        return false;
    }

    protected boolean hasOptionSet(String option) {
        return options.getOptions().containsKey(option);
    }
}
