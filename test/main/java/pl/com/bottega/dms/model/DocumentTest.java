package pl.com.bottega.dms.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import pl.com.bottega.dms.model.commands.ChangeDocumentCommand;
import pl.com.bottega.dms.model.commands.ConfirmDocumentCommand;
import pl.com.bottega.dms.model.commands.CreateDocumentCommand;
import pl.com.bottega.dms.model.commands.PublishDocumentCommand;
import pl.com.bottega.dms.model.numbers.NumberGenerator;
import pl.com.bottega.dms.model.printing.PrintCostCalculator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import static pl.com.bottega.dms.model.DocumentStatus.*;

@RunWith(MockitoJUnitRunner.class)
public class DocumentTest {

    @Mock
    private PrintCostCalculator printCostCalculator;

    @Test
    public void shouldBeDraftAfterCreate() {
        Document document = given().newDocument();

        assertEquals(DocumentStatus.DRAFT, document.getStatus());
    }

    @Test
    public void shouldGenerateNumberOnCreate() {
        Document document = given().newDocument();

        assertEquals(anyDocumentNumber(), document.getNumber());
    }

    @Test
    public void shouldSetTitleOnCreate() {
        Document document = given().newDocument();

        assertEquals("test title", document.getTitle());
    }

    @Test
    public void shouldChangeTitleAndContent() {
        Document document = given().newDocument();

        ChangeDocumentCommand changeDocumentCommand = new ChangeDocumentCommand();
        changeDocumentCommand.setTitle("changed title");
        changeDocumentCommand.setContent("changed content");
        document.change(changeDocumentCommand);

        assertEquals("changed title", document.getTitle());
        assertEquals("changed content", document.getContent());
    }

    @Test
    //1. Dokument po weryfikacji zmienia status na VERIFIED.
    public void shouldChangeStatusToVerified() {
        Document document = given().verifiedDocument();

        //then
        assertEquals(VERIFIED, document.getStatus());
    }

    @Test(expected = DocumentStatusException.class)
    //2. Dokument można zweryfikować tylko gdy jest w statusie DRAFT, próba weryfikacji w każdym innym statusie powinna wyrzucić wyjątek runtajmowy DocumentStatusException (stwórz klasę wyjątku).
    public void shouldNotAllowDoubleVerification() {
        //given
        Document document = given().verifiedDocument();

        //when
        document.verify(anyEmployeeId());
    }

    @Test
    //3. Dokument po edycji powinien wrócić do statusu DRAFT.
    public void shouldBeDraftAfterEdition() {
        // given verified document
        Document document = given().verifiedDocument();

        //when
        document.change(new ChangeDocumentCommand());

        // then
        assertEquals(DRAFT, document.getStatus());
    }

    @Test
    //4. Dokument po publickacji powinien zmienić status na PUBLISHED.
    public void shouldChangeStatusToPublishedOnPublication() {
        // given published document
        Document document = given().publishedDocument();

        // then
        assertEquals(PUBLISHED, document.getStatus());
    }

    @Test(expected = DocumentStatusException.class)
    //2 cd
    public void shouldNotAllowVerificationOfPublishedDocument() {
        //given - published document
        Document document = given().publishedDocument();

        //when
        document.verify(anyEmployeeId());
    }

    @Test(expected = DocumentStatusException.class)
    // 5. Dokumentu nie można edytować w statusie innym niż DRAFT i VERIFIED. Próba edycji w każdym innym statusie powinna wyrzucić wyjątek runtajmowy DocumentStatusException.
    public void shouldNotAllowEditionOfPublishedDocument() {
        //given - published document
        Document document = given().publishedDocument();

        //when
        document.change(new ChangeDocumentCommand());
    }

    @Test
    // 6. Dokument powinien pamiętać datę swojego stworzenia.
    public void shouldRememberCreationDate() {
        Document document = given().newDocument();

        assertSameTime(LocalDateTime.now(), document.getCreatedAt());
    }

    @Test
    //7. Doument powinien pamiętać datę ostatniej weryfikacji.
    public void shouldRememberLastVerificationDate() {
        Document document = given().verifiedDocument();

        //then
        assertSameTime(LocalDateTime.now(), document.getVerifiedAt());
    }

    @Test
    //      8. Dokument powinien pamiętać datę publikacji.
    public void shouldRememberPublicationDate() {
        Document document = given().publishedDocument();

        // then
        assertSameTime(LocalDateTime.now(), document.getPublishedAt());
    }

    @Test
    //9. Dokument powinien pamiętać datę ostatniej edycji.
    public void shouldRememberLastEditionDate() {
        Document document = given().newDocument();

        // when
        document.change(new ChangeDocumentCommand());

        // then
        assertSameTime(LocalDateTime.now(), document.getChangedAt());
    }

    //10. Dokument powinien pamiętać id pracownika, który go stworzył.
    @Test
    public void shouldRememberCreatorId() {
        Document document = given().newDocument();

        assertEquals(anyEmployeeId(), document.getCreatorId());
    }

    //11. Dokument powinien pamiętać id pracownika, który go ostatnio zweryfikował.
    @Test
    public void shouldRememberVerificatorId() {
        Document document = given().verifiedDocument();

        assertEquals(anyEmployeeId(), document.getVerifierId());
    }

    //12. Dokument powinien pamiętać id pracownika, który go ostatnio edytował.
    @Test
    public void shouldRememberEditorId() {
        Document document = given().newDocument();

        ChangeDocumentCommand changeDocumentCommand = new ChangeDocumentCommand();
        changeDocumentCommand.setEmployeeId(anyEmployeeId());
        document.change(changeDocumentCommand);

        assertEquals(anyEmployeeId(), document.getEditorId());
    }

    //13. Dokument powinien pamiętać id pracownika, który go opublikował.
    @Test
    public void shouldRememberPublisherId() {
        Document document = given().publishedDocument();

        assertEquals(anyEmployeeId(), document.getPublisherId());
    }

    @Test
    //14. Dokument można zarchiwizować w dowolnym statusie i wtedy zmienia on status na ARCHIVED.
    public void shouldAllowArchivingDraftDocuments() {
        Document document = given().newDocument();

        document.archive(anyEmployeeId());

        assertEquals(ARCHIVED, document.getStatus());
    }

    @Test
    //14. Dokument można zarchiwizować w dowolnym statusie i wtedy zmienia on status na ARCHIVED.
    public void shouldAllowArchivingVerifiedDocuments() {
        Document document = given().verifiedDocument();

        document.archive(anyEmployeeId());

        assertEquals(ARCHIVED, document.getStatus());
    }

    @Test
    //14. Dokument można zarchiwizować w dowolnym statusie i wtedy zmienia on status na ARCHIVED.
    public void shouldAllowArchivingPublishedDocuments() {
        Document document = given().publishedDocument();

        document.archive(anyEmployeeId());

        assertEquals(ARCHIVED, document.getStatus());
    }

    @Test(expected = DocumentStatusException.class)
    //15. Z dokumentem zarchiwizowanym nie można nic robić (edytować, publikować, weryfikować). Wszelkie próby powinny rzucać wyjątek DocumentStatusException.
    public void shouldNotAllowEditingArchivedDocuments() {
        Document document = given().archivedDocument();

        document.change(new ChangeDocumentCommand());
    }

    @Test(expected = DocumentStatusException.class)
    //15. Z dokumentem zarchiwizowanym nie można nic robić (edytować, publikować, weryfikować). Wszelkie próby powinny rzucać wyjątek DocumentStatusException.
    public void shouldNotAllowVerifyingArchivedDocuments() {
        Document document = given().archivedDocument();

        document.verify(anyEmployeeId());
    }

    @Test(expected = DocumentStatusException.class)
    //15. Z dokumentem zarchiwizowanym nie można nic robić (edytować, publikować, weryfikować). Wszelkie próby powinny rzucać wyjątek DocumentStatusException.
    public void shouldNotAllowPublishingArchivedDocuments() {
        Document document = given().archivedDocument();

        document.publish(new PublishDocumentCommand(), printCostCalculator);
    }

    @Test
    public void shouldCalculateCostOnPublish() {
        //given
        Document document = given().verifiedDocument();
        when(printCostCalculator.calculateCost(document)).thenReturn(new BigDecimal(50));

        //when
        PublishDocumentCommand publishDocumentCommand = new PublishDocumentCommand();
        publishDocumentCommand.setRecipients(Arrays.asList(new EmployeeId(1L)));
        document.publish(publishDocumentCommand, printCostCalculator);

        //then
        verify(printCostCalculator, times(1)).calculateCost(document);
        assertEquals(new BigDecimal(50), document.getPrintCost());
    }

    @Test(expected = DocumentStatusException.class)
    public void shouldNotAllowPublishingDraftDocuments() {
        Document document = given().newDocument();

        document.publish(new PublishDocumentCommand(), printCostCalculator);
    }

    @Test
    public void shouldAllowConfirming() {
        //given
        Document document = given().publishedDocument(new EmployeeId(1L));

        //when
        ConfirmDocumentCommand cmd = new ConfirmDocumentCommand();
        cmd.setEmployeeId(new EmployeeId(1L));
        document.confirm(cmd);

        // then
        assertTrue(document.isConfirmedBy(new EmployeeId(1L)));
    }

    private static final Long DATE_EPS = 500L;

    private void assertSameTime(LocalDateTime expected, LocalDateTime actual) {
        assertTrue(ChronoUnit.MILLIS.between(expected, actual) < DATE_EPS);
    }

    private DocumentAssembler given() {
        return new DocumentAssembler();
    }

    private DocumentNumber anyDocumentNumber() {
        return new DocumentNumber("1");
    }

    private EmployeeId anyEmployeeId() {
        return new EmployeeId(1L);
    }

    class DocumentAssembler {


        public Document newDocument() {
            EmployeeId employeeId = anyEmployeeId();
            CreateDocumentCommand cmd = new CreateDocumentCommand();
            cmd.setTitle("test title");
            cmd.setEmployeeId(employeeId);
            NumberGenerator numberGenerator = mock(NumberGenerator.class);
            when(numberGenerator.generate()).thenReturn(anyDocumentNumber());
            return new Document(cmd, numberGenerator);
        }

        public Document verifiedDocument() {
            Document document = newDocument();
            document.verify(anyEmployeeId());
            return document;
        }

        public Document publishedDocument() {
           return publishedDocument(new EmployeeId(1L));
        }

        public Document publishedDocument(EmployeeId... employeeIds) {
            Document document = verifiedDocument();
            PublishDocumentCommand cmd = new PublishDocumentCommand();
            cmd.setRecipients(Arrays.asList(employeeIds));
            cmd.setEmployeeId(anyEmployeeId());
            document.publish(cmd, printCostCalculator);
            return document;
        }

        public Document archivedDocument() {
            Document document = newDocument();
            document.archive(anyEmployeeId());
            return document;
        }
    }

}
