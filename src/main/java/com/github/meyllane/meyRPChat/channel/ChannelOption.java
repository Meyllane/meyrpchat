package com.github.meyllane.meyRPChat.channel;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class ChannelOption {
    private final Map<String, Object> options;

    private ChannelOption(Map<String, Object> customOptions) {
        this.options = Collections.unmodifiableMap(customOptions);
    }

    public Map<String, Object> getOptions() {
        return options;
    }

    public Object getOptionValue(String option) {
        return this.options.get(option);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final Map<String, Object> customOptions = new HashMap<>();

        public Builder addOption(String key, Object value) {
            this.customOptions.put(key, value);
            return this;
        }

        public Builder setCustomOptions(Map<String, Object> options) {
            this.customOptions.clear();
            if (options != null) {
                this.customOptions.putAll(options);
            }
            return this;
        }

        public ChannelOption build() {
            return new ChannelOption(new HashMap<>(customOptions));
        }
    }
}