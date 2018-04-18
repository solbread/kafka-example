package example;

import example.domain.ProducingData;
import example.producer.CustomProducer;
import example.producer.CustomProducerImpl;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class ProducerMain {

    private final Logger logger = Logger.getLogger(ProducerMain.class.getName());
    private final CustomProducer producer;

    public ProducerMain(Map<String, Object> configs) {
        this.producer = new CustomProducerImpl(configs);
    }

    public void start() {
        int idx=0;
        while(true) {
            try {
                ProducingData producingData = new ProducingData("test_topic2", "test-key"+idx, "test-message"+idx);
                this.producer.produce(producingData);
                Thread.sleep(1000);
                idx++;
            } catch (Exception e) {
                logger.error("occur exception in main : error message - " + e.getMessage(), e);
            }
        }
    }

    public static void main(String[] args) {
        Map<String, Object> configs = new HashMap<>();
        configs.put("bootstrap.servers", "localhost:9092");
        configs.put("key.serializer", StringSerializer.class.getName());
        configs.put("value.serializer", StringSerializer.class.getName());
        configs.put("acks", "all");

        ProducerMain main = new ProducerMain(configs);
        main.start();
    }
}
