package pl.com.bottega.dms.model.numbers;

import pl.com.bottega.dms.model.DocumentNumber;

public class AuditNumberGenerator implements NumberGenerator {

    private NumberGenerator numberGenerator;

    public AuditNumberGenerator(NumberGenerator numberGenerator) {
        this.numberGenerator = numberGenerator;
    }

    @Override
    public DocumentNumber generate() {
        DocumentNumber documentNumber = numberGenerator.generate();

        String nr = documentNumber.getNumber();
        return new DocumentNumber("AUDIT-" + nr);
    }
}
