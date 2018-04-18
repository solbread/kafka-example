package example.producer;

import example.domain.ProducingData;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class CustomProducerImplTest {

    @Test
    public void produce() {
        Map<String, Object> configs = new HashMap<>();
        configs.put("bootstrap.servers", "localhost:9092");
        configs.put("key.serializer", StringSerializer.class.getName()  );
        configs.put("value.serializer", StringSerializer.class.getName());
        configs.put("acks ", "all");

        ProducingData producingData = new ProducingData("test_topic", "test-key", "test-message");
        CustomProducer customProducer = new CustomProducerImpl(configs);
        customProducer.produce(producingData);
    }
}