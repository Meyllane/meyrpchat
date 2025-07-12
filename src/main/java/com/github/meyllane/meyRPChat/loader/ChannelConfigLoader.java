package com.github.meyllane.meyRPChat.loader;

import com.github.meyllane.meyRPChat.MeyRPChat;
import com.github.meyllane.meyRPChat.channel.ChannelOption;
import org.bukkit.configuration.file.FileConfiguration;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class ChannelConfigLoader {

    private static final MeyRPChat plugin = MeyRPChat.getPlugin(MeyRPChat.class);

    public static List<LoadedChannelConfig> loadedChannelConfigs(FileConfiguration config) {

        List<LoadedChannelConfig> result = new ArrayList<>();

        for (Map<?, ?> raw : config.getMapList("channel")) {

            String prefix = (String) raw.get("prefix");
            String type = (String) raw.get("type");
            String format = (String) raw.get("format");

            ChannelOption.Builder optBuilder = ChannelOption.builder();

            Object optObj = raw.get("options");
            if (optObj instanceof Map<?, ?> opt) {
                opt.keySet()
                        .forEach(key -> optBuilder.addOption((String) key, opt.get(key)));
            }

            result.add(new LoadedChannelConfig(prefix, type, format, optBuilder.build()));
        }
        return result;
    }

    public record LoadedChannelConfig(
            String prefix,
            String type,
            String format,
            ChannelOption options
    ) {}
}
