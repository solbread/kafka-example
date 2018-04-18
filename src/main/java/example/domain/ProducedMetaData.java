package example.domain;

public class ProducedMetaData {

    private final String topic;
    private final int partition;
    private final Long offset;
    private final Long timestamp;

    public ProducedMetaData(String topic, int partition, Long offset, Long timestamp) {
        this.topic = topic;
        this.partition = partition;
        this.offset = offset;
        this.timestamp = timestamp;
    }

    public String getTopic() {
        return topic;
    }

    public int getPartition() {
        return partition;
    }

    public Long getOffset() {
        return offset;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "ProducedMetaData{" +
                "topic='" + topic + '\'' +
                ", partition=" + partition +
                ", offset=" + offset +
                ", timestamp=" + timestamp +
                '}';
    }
}
