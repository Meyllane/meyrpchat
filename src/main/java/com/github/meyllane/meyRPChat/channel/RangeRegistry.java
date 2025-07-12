package com.github.meyllane.meyRPChat.channel;

import com.github.meyllane.meyRPChat.context.ext.Range;
import java.util.HashMap;
import java.util.Map;

public final class RangeRegistry {
    private final Map<String, Range> map = new HashMap<>();

    public void register(String prefix, Range range) { map.put(prefix, range); }

    public Map<String, Range> getMap() {
        return map;
    }
}