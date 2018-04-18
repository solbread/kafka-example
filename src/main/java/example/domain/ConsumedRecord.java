package example.domain;

import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.record.TimestampType;

public class ConsumedRecord {
    private final String topic;
    private final Object key;
    private final Object value;
    private final int partition;
    private final long offset;
    private final TimestampType timestampType;
    private final long timestamp;
    private final Headers headers;

    public ConsumedRecord(String topic, Object key, Object value, int partition, long offset, TimestampType timestampType, long timestamp, Headers headers) {
        this.topic = topic;
        this.key = key;
        this.value = value;
        this.partition = partition;
        this.offset = offset;
        this.timestampType = timestampType;
        this.timestamp = timestamp;
        this.headers = headers;
    }

    public String getTopic() {
        return topic;
    }

    public Object getKey() {
        return key;
    }

    public Object getValue() {
        return value;
    }

    public int getPartition() {
        return partition;
    }

    public long getOffset() {
        return offset;
    }

    public TimestampType getTimestampType() {
        return timestampType;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public Headers getHeaders() {
        return headers;
    }

    @Override
    public String toString() {
        return "ConsumedRecord{" +
                "topic='" + topic + '\'' +
                ", key=" + key +
                ", value=" + value +
                ", partition=" + partition +
                ", offset=" + offset +
                ", timestampType=" + timestampType +
                ", timestamp=" + timestamp +
                ", headers=" + headers +
                '}';
    }
}
