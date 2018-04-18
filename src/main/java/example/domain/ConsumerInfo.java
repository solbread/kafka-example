package example.domain;

import java.util.List;
import java.util.Map;

public class ConsumerInfo {
    private final Map<String, Object> configs;
    private final List<String> topicNames;
    private final long pollInterval;

    public ConsumerInfo(Map<String, Object> configs, List<String> topicNames, long pollInterval) {
        this.configs = configs;
        this.topicNames = topicNames;
        this.pollInterval = pollInterval;
    }

    public Map<String, Object> getConfigs() {
        return configs;
    }

    public List<String> getTopicNames() {
        return topicNames;
    }

    public long getPollInterval() {
        return pollInterval;
    }

}
