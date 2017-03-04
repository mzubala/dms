package pl.com.bottega.dms.model.numbers;

import org.springframework.stereotype.Component;
import pl.com.bottega.dms.model.DocumentNumber;

import java.util.UUID;

@Component
public class ISONumberGenerator implements NumberGenerator {

    public DocumentNumber generate() {
        return new DocumentNumber("ISO-" + UUID.randomUUID().toString());
    }

}
