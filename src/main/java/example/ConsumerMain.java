package example;

import example.consumer.CustomConsumer;
import example.consumer.CustomConsumerImpl;
import example.domain.ConsumedRecord;
import example.domain.ConsumerInfo;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConsumerMain {

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    private final CustomConsumer consumer;

    public ConsumerMain(CustomConsumer consumer) {
        this.consumer = consumer;
    }

    public void start() {
        while(true) {
            try {
                List<ConsumedRecord> records = this.consumer.conusme();
                for(ConsumedRecord record : records) {
                    logger.info(record);
                }
                Thread.sleep(5000);
            } catch (Exception e) {
                logger.error("occur exception in main : error message - " + e.getMessage(), e);
            }
        }
    }
    public static void main(String[] args) {
        Map<String, Object> configs = new HashMap<>();
        configs.put("key.deserializer", StringDeserializer.class.getName());
        configs.put("value.deserializer", StringDeserializer.class.getName());
        configs.put("bootstrap.servers", "localhost:9092");
        configs.put("group.id", "test_consumer3");
        configs.put("max.poll.records", 1);
        configs.put("client.id", "test_consumer3");

        List<String> topics = Arrays.asList(new String[]{"test_topic2"});

        ConsumerInfo consumerInfo = new ConsumerInfo(configs, topics, 1000);
        CustomConsumer customConsumer = new CustomConsumerImpl(consumerInfo);


        ConsumerMain main = new ConsumerMain(customConsumer);
        main.start();
    }
}
