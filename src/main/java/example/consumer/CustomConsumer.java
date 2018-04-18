package example.consumer;

import example.domain.ConsumedRecord;

import java.util.List;

public interface CustomConsumer {
    public List<ConsumedRecord> conusme();
}