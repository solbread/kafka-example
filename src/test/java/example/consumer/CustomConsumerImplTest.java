package example.consumer;

import example.domain.ConsumedRecord;
import example.domain.ConsumerInfo;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import static org.junit.Assert.*;

public class CustomConsumerImplTest {

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    public void run(CustomConsumer customConsumer) {
        while(true) {
            List<ConsumedRecord> records = customConsumer.conusme();
            for(ConsumedRecord record : records) {
                logger.info(record.toString());
            }
        }
    }

    @Test
    public void conusme() {
        Map<String, Object> configs = new HashMap<>();
        configs.put("key.deserializer", StringDeserializer.class.getName());
        configs.put("value.deserializer", StringDeserializer.class.getName());
        configs.put("bootstrap.servers", "localhost:9092");
        configs.put("group.id", "test");

        List<String> topics = Arrays.asList(new String[]{"test_topic"});

        ConsumerInfo consumerInfo = new ConsumerInfo(configs, topics, 1000);
        CustomConsumer customConsumer = new CustomConsumerImpl(consumerInfo);

        CustomConsumerImplTest test = new    CustomConsumerImplTest();
        test.run(customConsumer);


    }
}