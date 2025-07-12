package com.github.meyllane.meyRPChat.channel;

import com.github.meyllane.meyRPChat.context.ChatContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ChannelRegistry {
    private final HashMap<String, ChannelDescriptor<? extends ChatContext>> map = new HashMap<>();

    public <C extends ChatContext> void register(
            String prefix,
            ChannelDescriptor<C> desc
    ) {
        map.put(prefix, desc);
    }

    public ChannelDescriptor<? extends ChatContext> getByPrefix(String prefix) {
        return map.get(prefix);
    }

    public ResolvedChannel resolve(String rawMessage) {
        ArrayList<String> sortedKeys = new ArrayList<>(map.keySet()).stream().
                filter(key -> !key.isEmpty())
                .sorted((a, b) -> Integer.compare(b.length(), a.length()))
                .collect(Collectors.toCollection(ArrayList::new));

        String prefix = "";
        Matcher matcher = Pattern.compile("^[^a-zA-Z0-9]+").matcher(rawMessage);
        if (matcher.find()) {
            prefix = matcher.group();
        }

        for (String key : sortedKeys) {
            if (prefix.contains(key)) {
                ChannelDescriptor<? extends ChatContext> desc = map.get(key);
                // Retire la première occurrence de la clé dans le préfixe
                String cleaned = rawMessage.replaceFirst(Pattern.quote(key), "");
                return new ResolvedChannel(desc, cleaned);
            }
        }
        ChannelDescriptor<? extends ChatContext> desc = map.get("");
        return new ResolvedChannel(desc, rawMessage);
    }

    public record ResolvedChannel(
            ChannelDescriptor<? extends ChatContext> descriptor,
            String cleanedMessage
    ) {}
}
