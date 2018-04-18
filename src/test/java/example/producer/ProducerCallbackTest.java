package example.producer;

import org.apache.kafka.clients.producer.RecordMetadata;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ProducerCallbackTest {

    @Test
    public void onCompletion(RecordMetadata recordMetadata, Exception e) {
        assertNull(e);
    }
}