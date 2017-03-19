package pl.com.bottega.dms.model;

import org.junit.Test;
import pl.com.bottega.dms.model.numbers.AuditNumberGenerator;
import pl.com.bottega.dms.model.numbers.DemoNumberGenerator;
import pl.com.bottega.dms.model.numbers.NumberGenerator;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class NumberGeneratorTest {

    @Test
    public void shouldGenerateNumber() {
        // given
        NumberGenerator numberGenerator = new DemoNumberGenerator(
                new AuditNumberGenerator(
                        new TestNumberGenerator()
                ));

        //when
        DocumentNumber nr = numberGenerator.generate();

        //then
        assertThat(nr).isEqualTo(new DocumentNumber("DEMO-AUDIT-test"));
    }

    class TestNumberGenerator implements NumberGenerator {

        @Override
        public DocumentNumber generate() {
            return new DocumentNumber("test");
        }
    }

}
