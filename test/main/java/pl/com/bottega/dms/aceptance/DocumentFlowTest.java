package pl.com.bottega.dms.aceptance;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.com.bottega.dms.DmsApplication;
import pl.com.bottega.dms.application.DocumentCatalog;
import pl.com.bottega.dms.application.DocumentDto;
import pl.com.bottega.dms.application.DocumentFlowProcess;
import pl.com.bottega.dms.application.user.AuthProcess;
import pl.com.bottega.dms.application.user.CreateAccountCommand;
import pl.com.bottega.dms.application.user.LoginCommand;
import pl.com.bottega.dms.model.DocumentNumber;
import pl.com.bottega.dms.model.EmployeeId;
import pl.com.bottega.dms.model.commands.ChangeDocumentCommand;
import pl.com.bottega.dms.model.commands.CreateDocumentCommand;
import pl.com.bottega.dms.model.commands.PublishDocumentCommand;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class DocumentFlowTest {

    @Autowired
    private DocumentFlowProcess documentFlowProcess;

    @Autowired
    private DocumentCatalog documentCatalog;

    @Autowired
    private AuthProcess authProcess;

    @Before
    public void authenticate() {
        CreateAccountCommand cmd = new CreateAccountCommand();
        cmd.setUserName("janek");
        cmd.setEmployeeId(1L);
        cmd.setPassword("xxx");
        authProcess.createAccount(cmd);

        LoginCommand loginCommand = new LoginCommand();
        loginCommand.setLogin("janek");
        loginCommand.setPassword("xxx");
        authProcess.login(loginCommand);
    }

    @Test
    public void shouldCreateDocument() {
        // when - I create document
        DocumentNumber documentNumber = createDocument();

        // then - the document is available in catalog
        DocumentDto dto = documentCatalog.get(documentNumber);
        assertThat(dto.getTitle()).isEqualTo("test");
        assertThat(dto.getNumber()).isEqualTo(documentNumber.getNumber());
    }

    @Test
    public void shouldUpdateDocument() {
        // given a document
        DocumentNumber documentNumber = createDocument();

        // when
        ChangeDocumentCommand changeDocumentCommand = new ChangeDocumentCommand();
        changeDocumentCommand.setNumber(documentNumber.getNumber());
        changeDocumentCommand.setTitle("new title");
        changeDocumentCommand.setContent("new content");
        documentFlowProcess.change(changeDocumentCommand);

        // then
        DocumentDto documentDto = documentCatalog.get(documentNumber);
        assertThat(documentDto.getTitle()).isEqualTo("new title");
        assertThat(documentDto.getContent()).isEqualTo("new content");
    }

    @Test
    public void shouldVerifyDocument() {
        //given
        DocumentNumber documentNumber = createDocument();

        //when
        documentFlowProcess.verify(documentNumber);

        //then
        DocumentDto documentDto = documentCatalog.get(documentNumber);
        assertThat(documentDto.getStatus()).isEqualTo("VERIFIED");
    }

    @Test
    public void shouldArchiveDocument() {
        //given
        DocumentNumber documentNumber = createDocument();

        //when
        documentFlowProcess.archive(documentNumber);

        //then
        DocumentDto documentDto = documentCatalog.get(documentNumber);
        assertThat(documentDto.getStatus()).isEqualTo("ARCHIVED");
    }

    @Test
    public void shouldPublishDocument() {
        //given
        DocumentNumber documentNumber = createDocument();
        documentFlowProcess.verify(documentNumber);

        //when
        PublishDocumentCommand cmd = new PublishDocumentCommand();
        cmd.setNumber(documentNumber.getNumber());
        cmd.setRecipients(Arrays.asList(new EmployeeId(1L)));
        documentFlowProcess.publish(cmd);

        //then
        DocumentDto documentDto = documentCatalog.get(documentNumber);
        assertThat(documentDto.getStatus()).isEqualTo("PUBLISHED");
    }

    private DocumentNumber createDocument() {
        CreateDocumentCommand cmd = new CreateDocumentCommand();
        cmd.setTitle("test");
        return documentFlowProcess.create(cmd);
    }

}
