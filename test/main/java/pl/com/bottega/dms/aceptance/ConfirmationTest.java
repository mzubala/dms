package pl.com.bottega.dms.aceptance;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import pl.com.bottega.dms.application.*;
import pl.com.bottega.dms.model.DocumentNumber;
import pl.com.bottega.dms.model.DocumentType;
import pl.com.bottega.dms.model.EmployeeId;
import pl.com.bottega.dms.model.commands.*;
import pl.com.bottega.dms.shared.AuthHelper;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
public class ConfirmationTest {

    @Autowired
    private DocumentFlowProcess documentFlowProcess;

    @Autowired
    private DocumentCatalog documentCatalog;

    @Autowired
    private ReadingConfirmator readingConfirmator;

    @Autowired
    private AuthHelper authHelper;

    @Test
    public void shouldConfirmDocument() {
        // given
        authHelper.authenticate();
        DocumentNumber documentNumber = publishedDocument();

        //when
        ConfirmDocumentCommand confirmDocumentCommand = new ConfirmDocumentCommand();
        confirmDocumentCommand.setEmployeeId(new EmployeeId(1L));
        confirmDocumentCommand.setNumber(documentNumber.getNumber());
        readingConfirmator.confirm(confirmDocumentCommand);

        //then
        DocumentDto dto = documentCatalog.get(documentNumber);
        assertThat(dto.getConfirmations().size()).isEqualTo(1);
        ConfirmationDto confirmationDto = dto.getConfirmations().get(0);
        assertThat(confirmationDto.isConfirmed()).isTrue();
        assertThat(confirmationDto.getOwnerEmployeeId()).isEqualTo(1L);
    }

    @Test
    public void shouldConfirmDocumentForAnotherEmployee() {
        // given
        authHelper.authenticate(0L);
        DocumentNumber documentNumber = publishedDocument();

        //when
        ConfirmForDocumentCommand confirmDocumentCommand = new ConfirmForDocumentCommand();
        confirmDocumentCommand.setConfirmForEmployeeId(new EmployeeId(1L));
        confirmDocumentCommand.setNumber(documentNumber.getNumber());
        readingConfirmator.confirmFor(confirmDocumentCommand);

        //then
        DocumentDto dto = documentCatalog.get(documentNumber);
        assertThat(dto.getConfirmations().size()).isEqualTo(1);
        ConfirmationDto confirmationDto = dto.getConfirmations().get(0);
        assertThat(confirmationDto.isConfirmed()).isTrue();
        assertThat(confirmationDto.getOwnerEmployeeId()).isEqualTo(1L);
        assertThat(confirmationDto.getProxyEmployeeId()).isEqualTo(0L);
    }

    private DocumentNumber publishedDocument() {
        DocumentNumber documentNumber = createDocument();
        updateDocument(documentNumber);
        documentFlowProcess.verify(documentNumber);
        PublishDocumentCommand publishDocumentCommand = new PublishDocumentCommand();
        publishDocumentCommand.setNumber(documentNumber.getNumber());
        publishDocumentCommand.setRecipients(Arrays.asList(new EmployeeId(1L)));
        documentFlowProcess.publish(publishDocumentCommand);
        return documentNumber;
    }

    private DocumentNumber createDocument() {
        CreateDocumentCommand cmd = new CreateDocumentCommand();
        cmd.setTitle("test");
        cmd.setDocumentType(DocumentType.MANUAL);
        return documentFlowProcess.create(cmd);
    }

    private void updateDocument(DocumentNumber nr) {
        ChangeDocumentCommand cmd = new ChangeDocumentCommand();
        cmd.setNumber(nr.getNumber());
        cmd.setContent("blah blah");
        cmd.setExpiresAt(LocalDateTime.now().plusDays(365L));
        cmd.setTitle("test");
        documentFlowProcess.change(cmd);
    }

}
