package example.domain;

public class ProducingData {
    private final String topicName;
    private final String key;
    private final Object message;

    public ProducingData(String topicName, String key, Object message) {
        this.topicName = topicName;
        this.key = key;
        this.message = message;
    }

    public String getTopicName() {
        return topicName;
    }

    public String getKey() {
        return key;
    }

    public Object getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "ProduceData{" +
                "topicName='" + topicName + '\'' +
                ", key='" + key + '\'' +
                ", message=" + message +
                '}';
    }
}
