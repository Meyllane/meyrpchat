package com.github.meyllane.meyRPChat.loader;

import com.github.meyllane.meyRPChat.MeyRPChat;
import com.github.meyllane.meyRPChat.context.ext.Range;
import org.bukkit.plugin.Plugin;

import java.util.*;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class RangeLoader {
    private static final Plugin plugin = MeyRPChat.getPlugin(MeyRPChat.class);

    public static List<Range> getRanges() {
        var ranges = loadRangeFromConfig();
        var validRanges = removeDuplicatePrefixes(ranges);
        var singleDefaultRanges = ensureSingleDefault(validRanges);
        return removeInvalidRanges(singleDefaultRanges);
    }

    private static List<Range> loadRangeFromConfig() {
        return plugin.getConfig().getMapList("range").stream()
                .map(map -> new Range(
                        (String) map.get("prefix"),
                        (int) map.get("range"),
                        (String) map.get("descriptor"),
                        (String) map.get("color"),
                        (boolean) map.get("default")
                ))
                .toList();
    }

    private static List<Range> removeDuplicatePrefixes(List<Range> ranges) {
        var prefixCounts = ranges.stream()
                .collect(Collectors.groupingBy(Range::prefix, Collectors.counting()));

        var duplicates = prefixCounts.entrySet().stream()
                .filter(e -> e.getValue() > 1)
                .map(Map.Entry::getKey)
                .toList();

        logDuplicates(duplicates);

        return ranges.stream()
                .filter(range -> !duplicates.contains(range.prefix()))
                .toList();
    }

    private static List<Range> ensureSingleDefault(List<Range> ranges) {
        var defaults = ranges.stream()
                .filter(Range::isDefault)
                .toList();

        if (defaults.size() <= 1) return ranges;

        var toRemove = defaults.subList(1, defaults.size());
        logMultipleDefaults(toRemove);

        return ranges.stream()
                .filter(r -> !toRemove.contains(r))
                .toList();
    }

    private static List<Range> removeInvalidRanges(List<Range> ranges) {
        var invalidRanges = ranges.stream()
                .filter(RangeLoader::isAnyFieldNull)
                .toList();

        logInvalidRanges(invalidRanges);

        return ranges.stream()
                .filter(range -> !invalidRanges.contains(range))
                .toList();
    }

    private static void logMultipleDefaults(List<Range> defaults) {
        plugin.getLogger().log(
                Level.SEVERE,
                "Multiple Range objects are set as default. Only the first Range will be kept. The removed Ranges are:"
        );
        defaults.forEach(range ->
                plugin.getLogger().log(Level.SEVERE,
                        String.format("%s", range))
        );
    }

    private static void logDuplicates(List<String> duplicates) {
        duplicates.forEach(dup ->
                plugin.getLogger().log(Level.SEVERE,
                        String.format("Two or more Range objects have the prefix %s. The conflicting Range objects have been disabled.", dup))
        );
    }

    private static void logInvalidRanges(List<Range> invalidRanges) {
        invalidRanges.forEach(range ->
                plugin.getLogger().log(Level.SEVERE,
                        String.format("The Range object %s is invalid.", range))
        );
        if (!invalidRanges.isEmpty())
            plugin.getLogger().log(Level.SEVERE, "All invalid Range objects have been disabled.");
    }

    public static boolean isAnyFieldNull(Range range) {
        return Arrays.stream(Range.class.getRecordComponents())
                .anyMatch(component -> {
                    try {
                        return component.getAccessor().invoke(range) == null;
                    } catch (Exception e) {
                        return false;
                    }
                });
    }
}