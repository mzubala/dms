package pl.com.bottega.dms.model.numbers;

import pl.com.bottega.dms.model.DocumentNumber;

import java.util.UUID;

public class QEPNumberGenerator implements NumberGenerator {
    public DocumentNumber generate() {
        return new DocumentNumber("QEP-" + UUID.randomUUID().toString());
    }
}
