package com.github.meyllane.meyRPChat.context.ext;

public record Range(
        String prefix,
        int range,
        String descriptor,
        String color,
        boolean isDefault
) {
    public String toString() {
        return String.format(
                "Range(prefix = %s, range = %d, descriptor = %s, color = %s, isDefault = %s)",
                this.prefix, this.range, this.descriptor, this.color, this.isDefault
        );
    }
}
