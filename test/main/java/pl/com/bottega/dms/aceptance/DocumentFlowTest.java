package pl.com.bottega.dms.aceptance;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.com.bottega.dms.DmsApplication;
import pl.com.bottega.dms.application.DocumentCatalog;
import pl.com.bottega.dms.application.DocumentDto;
import pl.com.bottega.dms.application.DocumentFlowProcess;
import pl.com.bottega.dms.model.DocumentNumber;
import pl.com.bottega.dms.model.commands.CreateDocumentCommand;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(DmsApplication.class)
public class DocumentFlowTest {

    @Autowired
    private DocumentFlowProcess documentFlowProcess;

    @Autowired
    private DocumentCatalog documentCatalog;

    @Test
    public void shouldCreateDocument() {
        // when - I create document
        CreateDocumentCommand cmd = new CreateDocumentCommand();
        cmd.setTitle("test");
        DocumentNumber documentNumber = documentFlowProcess.create(cmd);

        // then - the document is available in catalog
        DocumentDto dto = documentCatalog.get(documentNumber);
        assertThat(dto.getTitle()).isEqualTo("test");
        assertThat(dto.getNumber()).isEqualTo(documentNumber.getNumber());
    }

}
