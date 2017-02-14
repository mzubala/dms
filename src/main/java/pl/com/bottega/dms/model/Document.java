package pl.com.bottega.dms.model;

import pl.com.bottega.dms.model.commands.*;
import pl.com.bottega.dms.model.numbers.NumberGenerator;
import pl.com.bottega.dms.model.printing.PrintCostCalculator;

import java.time.LocalDateTime;

import static pl.com.bottega.dms.model.DocumentStatus.*;

public class Document {

    private DocumentNumber number;
    private DocumentStatus status;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime verifiedAt;
    private LocalDateTime publishedAt;
    private LocalDateTime changedAt;
    private EmployeeId creatorId;
    private EmployeeId verifierId;
    private EmployeeId editorId;
    private EmployeeId publisherId;

    public Document(CreateDocumentCommand cmd, NumberGenerator numberGenerator) {
        this.number = numberGenerator.generate();
        this.status = DRAFT;
        this.title = cmd.getTitle();
        this.createdAt = LocalDateTime.now();
        this.creatorId = cmd.getEmployeeId();
    }

    public void change(ChangeDocumentCommand cmd) {
        if (!this.status.equals(DRAFT) && !this.status.equals(VERIFIED))
            throw new DocumentStatusException("Document should be DRAFT or VERIFIED to PUBLISH");
        this.title = cmd.getTitle();
        this.content = cmd.getContent();
        this.status = DRAFT;
        this.changedAt = LocalDateTime.now();
        this.editorId = cmd.getEmployeeId();
    }

    public void verify(EmployeeId employeeId) {
        if (!this.status.equals(DRAFT))
            throw new DocumentStatusException("Document should be DRAFT to VERIFY");
        this.status = VERIFIED;
        this.verifiedAt = LocalDateTime.now();
        this.verifierId = employeeId;
    }

    public void archive(EmployeeId employeeId) {
        this.status = ARCHIVED;
    }

    public void publish(PublishDocumentCommand cmd, PrintCostCalculator printCostCalculator) {
        if(!this.status.equals(VERIFIED))
            throw new DocumentStatusException("Document should be VERIFIED to PUBLISH");
        this.status = PUBLISHED;
        this.publishedAt = LocalDateTime.now();
        this.publisherId = cmd.getEmployeeId();
    }

    public void confirm(ConfirmDocumentCommand cmd) {

    }

    public void confirmFor(ConfirmForDocumentCommand cmd) {

    }

    public DocumentStatus getStatus() {
        return status;
    }

    public DocumentNumber getNumber() {
        return number;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getVerifiedAt() {
        return verifiedAt;
    }

    public LocalDateTime getPublishedAt() {
        return publishedAt;
    }

    public LocalDateTime getChangedAt() {
        return changedAt;
    }

    public EmployeeId getCreatorId() {
        return creatorId;
    }

    public EmployeeId getVerifierId() {
        return verifierId;
    }

    public EmployeeId getEditorId() {
        return editorId;
    }

    public EmployeeId getPublisherId() {
        return publisherId;
    }
}
