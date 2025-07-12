package com.github.meyllane.meyRPChat.tag;

import com.github.meyllane.meyRPChat.context.ChatContext;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

import java.util.ArrayList;
import java.util.HashMap;

public class TagProviderRegistry {
    private final ArrayList<TagProvider> registry = new ArrayList<>();
    private final HashMap<String, TagProvider> map = new HashMap<>();

    public void register(String id, TagProvider provider) {
        map.put(id, provider);
    }

    public TagResolver build(ChatContext ctx) {
        TagResolver.Builder builder = TagResolver.builder();

        this.map.keySet().forEach(
                key -> this.map.get(key).registerTag(builder, ctx)
        );

        return builder.build();
    }
}
