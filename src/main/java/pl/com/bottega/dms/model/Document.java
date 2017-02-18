package pl.com.bottega.dms.model;

import pl.com.bottega.dms.model.commands.*;
import pl.com.bottega.dms.model.numbers.NumberGenerator;
import pl.com.bottega.dms.model.printing.PrintCostCalculator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

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
    private BigDecimal printCost;
    private Set<Confirmation> confirmations;

    public Document(CreateDocumentCommand cmd, NumberGenerator numberGenerator) {
        this.number = numberGenerator.generate();
        this.status = DRAFT;
        this.title = cmd.getTitle();
        this.createdAt = LocalDateTime.now();
        this.creatorId = cmd.getEmployeeId();
        this.confirmations = new HashSet<>();
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
        if (!this.status.equals(VERIFIED))
            throw new DocumentStatusException("Document should be VERIFIED to PUBLISH");
        this.status = PUBLISHED;
        this.publishedAt = LocalDateTime.now();
        this.publisherId = cmd.getEmployeeId();
        this.printCost = printCostCalculator.calculateCost(this);
        createConfirmations(cmd);
    }

    private void createConfirmations(PublishDocumentCommand cmd) {
        for (EmployeeId employeeId : cmd.getRecipients()) {
            confirmations.add(new Confirmation(employeeId));
        }
    }

    public void confirm(ConfirmDocumentCommand cmd) {
        for (Confirmation confirmation : confirmations)
            if (confirmation.isOwnedBy(cmd.getEmployeeId())) {
                confirmation.confirm();
                return;
            }
        throw new DocumentStatusException(String.format("Document not published for %s", cmd.getEmployeeId()));
    }

    public void confirmFor(ConfirmForDocumentCommand cmd) {
        for (Confirmation confirmation : confirmations)
            if (confirmation.isOwnedBy(cmd.getEmployeeId())) {
                confirmation.confirmFor(cmd.getConfirmingEmployeeId());
                return;
            }
        throw new DocumentStatusException(String.format("Document not published for %s", cmd.getEmployeeId()));
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

    public BigDecimal getPrintCost() {
        return printCost;
    }

    public void setPrintCost(BigDecimal printCost) {
        this.printCost = printCost;
    }

    public boolean isConfirmedBy(EmployeeId employeeId) {
        for (Confirmation confirmation : confirmations) {
            if (confirmation.isOwnedBy(employeeId))
                return confirmation.isConfirmed();
        }
        return false;
    }
}
