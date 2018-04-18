package example.consumer;

import example.domain.ConsumedRecord;
import example.domain.ConsumerInfo;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.record.TimestampType;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.log4j.Logger;

import java.util.*;

public class CustomConsumerImpl implements  CustomConsumer {
    private final Logger logger = Logger.getLogger(this.getClass());

    private final Consumer<Object, Object> consumer;
    private final ConsumerInfo consumerInfo;

    public CustomConsumerImpl(ConsumerInfo consumerInfo) {
        this.consumer = new KafkaConsumer(consumerInfo.getConfigs());
        this.consumerInfo = consumerInfo;
    }

    public List<ConsumedRecord> conusme() {
        List<ConsumedRecord> consumedRecords = new ArrayList<>();
        this.consumer.subscribe(consumerInfo.getTopicNames());
        ConsumerRecords<Object, Object> records = this.consumer.poll(this.consumerInfo.getPollInterval());
        for(ConsumerRecord<Object, Object> record : records) {
            try {
                String topic = record.topic();
                Object key = record.key();
                Object value = record.value();
                int partition = record.partition();
                long offset = record.offset();
                TimestampType timestampType = record.timestampType();
                long timestamp = record.timestamp();
                Headers headers = record.headers();
                consumedRecords.add(new ConsumedRecord(topic,key,value,partition, offset, timestampType, timestamp, headers));

            } catch (Exception e) {
                logger.error("consume message failed : " + this.consumerInfo + ", error message - " + e.getMessage(), e);
            }
        }
        return consumedRecords;
    }
}
