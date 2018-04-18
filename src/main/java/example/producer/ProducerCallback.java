package example.producer;

import example.domain.ProducedMetaData;
import example.domain.ProducingData;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.log4j.Logger;

public class ProducerCallback implements Callback {
    private final Logger logger = Logger.getLogger(this.getClass());

    private final ProducingData producingData;

    public ProducerCallback(ProducingData producingData) {
        this.producingData = producingData;
    }

    public void onCompletion(RecordMetadata recordMetadata, Exception e) {
        if (e == null) {
            doSuccessTask(recordMetadata);
        } else {
            doFailureTask(e);
        }
    }

    private void doSuccessTask(RecordMetadata recordMetadata) {
        String topic = recordMetadata.topic();
        int partition = recordMetadata.partition();
        Long offset = recordMetadata.hasOffset() ? recordMetadata.offset() : null;
        Long timestamp = recordMetadata.hasTimestamp() ? recordMetadata.timestamp() : null;

        ProducedMetaData producedMetaData = new ProducedMetaData(topic, partition, offset, timestamp);

        logger.info("produce kafka message success : " + this.producingData + ", " + producedMetaData);
    }

    private void doFailureTask(Exception e) {
        logger.error("produce kafka message failed : error message - " + e.getMessage(), e);
    }
}
