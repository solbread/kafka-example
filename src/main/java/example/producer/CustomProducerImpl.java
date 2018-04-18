package example.producer;

import example.domain.ProducingData;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

public class CustomProducerImpl implements  CustomProducer {
    private final Logger logger = Logger.getLogger(this.getClass());
    private Producer<String, String> producer;

    public CustomProducerImpl(Map<String, Object> config) {
        this.producer = new KafkaProducer<>(config);
    }

    public void produce(ProducingData producingData) {
        try {
            String topicName = producingData.getTopicName();
            String key = producingData.getKey();
            String message = (String)producingData.getMessage();
            producer.send(new ProducerRecord<>(topicName, key, message), getCallback(producingData));
        } catch (Exception e) {
            logger.error("kafka task error : " + producingData + ", error message - " + e.getMessage(), e);
        }
    }

    private Callback getCallback(ProducingData producingData) {
        return new ProducerCallback(producingData);
    }
}
