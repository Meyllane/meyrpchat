package com.github.meyllane.meyRPChat.listener;

import com.github.meyllane.meyRPChat.MeyRPChat;
import com.github.meyllane.meyRPChat.channel.ChannelDescriptor;
import com.github.meyllane.meyRPChat.channel.ChannelRegistry;
import com.github.meyllane.meyRPChat.context.ChatContext;
import com.github.meyllane.meyRPChat.utils.PluginHeader;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;

public class ChatListener implements Listener {
    private static final MeyRPChat plugin = MeyRPChat.getPlugin(MeyRPChat.class);

    @EventHandler(priority = EventPriority.LOWEST)
    public void onAsyncChat(AsyncChatEvent event) {
        event.setCancelled(true);
        String plain = PlainTextComponentSerializer.plainText().serialize(event.message());
        if (plain.isEmpty()) return;

        ChannelRegistry.ResolvedChannel result = MeyRPChat.channelRegistry.resolve(plain);

        if (result.descriptor() == null) return;

        plugin.getServer().getScheduler().runTask(plugin, () -> dispatch(
                result.descriptor(),
                event.getPlayer(),
                result.cleanedMessage()
                ));
    }

    private <C extends ChatContext> void dispatch(ChannelDescriptor<C> desc, Player player, String message) {
        try {
            C ctx;
            List<String> chatBuffer = MeyRPChat.chatBuffer.computeIfAbsent(player.getUniqueId(), uuid -> new ArrayList<>());

            if (message.endsWith(">")) {
                ctx = desc.builder().build(player, message);
                chatBuffer.add(stripEnding(ctx));
                return;
            }

            if (!chatBuffer.isEmpty()) {
                ctx = desc.builder().build(player, message);
                chatBuffer.add(stripEnding(ctx));
                String joinMessage = String.join(" ", chatBuffer);
                chatBuffer.clear();
                ctx.setMessage(Component.text(joinMessage));
            } else {
                ctx = desc.builder().build(player, message);
            }

            desc.channel().send(ctx);
        } catch (RuntimeException e) {
            Audience.audience(player).sendMessage(PluginHeader.buildErrorComponent(e));
        }
    }

    private <C extends ChatContext> String stripEnding(C ctx) {
        String serialized = PlainTextComponentSerializer.plainText().serialize(ctx.getMessage());
        return serialized.replaceFirst(">$", "");
    }
}
