package com.github.meyllane.meyRPChat;

import com.github.meyllane.meyRPChat.builder.context.impl.BaseContextBuilder;
import com.github.meyllane.meyRPChat.builder.context.impl.TargetedContextBuilder;
import com.github.meyllane.meyRPChat.channel.ChannelDescriptor;
import com.github.meyllane.meyRPChat.channel.ChannelRegistry;
import com.github.meyllane.meyRPChat.channel.RangeRegistry;
import com.github.meyllane.meyRPChat.builder.channeldescriptor.ChannelDescriptorBuilder;
import com.github.meyllane.meyRPChat.builder.context.impl.RangedContextBuilder;
import com.github.meyllane.meyRPChat.channel.impl.ServerChannel;
import com.github.meyllane.meyRPChat.channel.impl.TargetedChannel;
import com.github.meyllane.meyRPChat.channel.impl.WorldChannel;
import com.github.meyllane.meyRPChat.context.ext.Range;
import com.github.meyllane.meyRPChat.channel.impl.RangedChannel;
import com.github.meyllane.meyRPChat.listener.ChatListener;
import com.github.meyllane.meyRPChat.loader.ChannelConfigLoader;
import com.github.meyllane.meyRPChat.loader.RangeLoader;
import com.github.meyllane.meyRPChat.tag.TagProviderRegistry;
import com.github.meyllane.meyRPChat.tag.impl.*;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

public final class MeyRPChat extends JavaPlugin {
    public List<Range> ranges;
    public static final ChannelRegistry channelRegistry = new ChannelRegistry();
    public static final RangeRegistry rangeRegistry = new RangeRegistry();
    public static Range defaultRange;
    public static final HashMap<String, ChannelDescriptorBuilder> channelConfigMap = new HashMap<>();
    public static final TagProviderRegistry tagProviderRegistry = new TagProviderRegistry();
    public static final MiniMessage mm = MiniMessage.miniMessage();
    public static String sendDeniedMessage;
    public static HashMap<UUID, ArrayList<String>> chatBuffer = new HashMap<>();

    @Override
    public void onEnable() {
        saveDefaultConfig();
        FileConfiguration config = getConfig();
        sendDeniedMessage = config.getString("send_denied_message", "You don\\'t have the permission to send a message on this channel.");

        reloadConfigurations();
        loadTagProviders();
        getServer().getPluginManager().registerEvents(new ChatListener(), this);
    }

    public void reloadConfigurations() {
        FileConfiguration config = getConfig();
        sendDeniedMessage = config.getString("send_denied_message", "You don\\'t have the permission to send a message on this channel.");

        loadRanges();
        loadChannelConfig(config);

        getLogger().info("Loading complete.");
    }

    private void loadRanges() {
        this.ranges = RangeLoader.getRanges();
        this.ranges.forEach(range -> {
            getLogger().log(Level.INFO, String.format("Loaded %s", range));
            rangeRegistry.register(range.prefix(), range);
        });
        defaultRange = this.ranges.stream()
                .filter(Range::isDefault)
                .findFirst()
                .orElse(null);
    }

    private void loadChannelConfig(FileConfiguration config) {
        channelConfigMap.put(
                "ranged",
                (format, options) -> new ChannelDescriptor<>(
                        new RangedChannel(format, options),
                        new RangedContextBuilder()
                )
        );
        channelConfigMap.put(
                "targeted",
                (format, options) -> new ChannelDescriptor<>(
                        new TargetedChannel(format, options),
                        new TargetedContextBuilder()
                )
        );
        channelConfigMap.put(
                "server",
                (format, options) -> new ChannelDescriptor<>(
                        new ServerChannel(format, options),
                        new BaseContextBuilder()
                )
        );
        channelConfigMap.put(
                "world",
                (format, options) -> new ChannelDescriptor<>(
                        new WorldChannel(format, options),
                        new BaseContextBuilder()
                )
        );

        List<ChannelConfigLoader.LoadedChannelConfig> loadedChannelConfigs = ChannelConfigLoader.loadedChannelConfigs(config);
        loadedChannelConfigs.forEach(channel -> {
            ChannelDescriptorBuilder builder = channelConfigMap.get(channel.type());
            if (builder == null) return;
            ChannelDescriptor<?> desc = builder.build(channel.format(), channel.options());
            channelRegistry.register(channel.prefix(), desc);

            addPermissionIfExists(channel, "sendPermission");
            addPermissionIfExists(channel, "receivePermission");
        });
    }

    private void addPermissionIfExists(ChannelConfigLoader.LoadedChannelConfig channel, String key) {
        if (channel.options().getOptions().containsKey(key)) {
            String permission = (String) channel.options().getOptions().get(key);
            getServer().getPluginManager().addPermission(new Permission(permission));
        }
    }

    private void loadTagProviders() {
        tagProviderRegistry.register("displayname", new DisplaynameTagProvider("displayname"));
        tagProviderRegistry.register("descriptor", new DescriptorTagProvider("descriptor"));
        tagProviderRegistry.register("message", new MessageTagProvider("message"));
        tagProviderRegistry.register("rangecolor", new RangeColorTagProvider("rangecolor"));
        tagProviderRegistry.register("rangeprefix", new RangePrefixTagProvider("rangeprefix"));
        tagProviderRegistry.register("target", new TargetTagProvider("target"));
    }

    @Override
    public void onDisable() {
        // Logique d'arrêt du plugin si besoin
    }
}