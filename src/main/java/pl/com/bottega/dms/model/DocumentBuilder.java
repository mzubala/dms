package pl.com.bottega.dms.model;

import java.time.LocalDateTime;

public interface DocumentBuilder {

    void buildTitle(String title);

    void buildContent(String content);

    void buildNumber(DocumentNumber documentNumber);

    void buildStatus(DocumentStatus documentStatus);

    void buildType(DocumentType documentType);

    void buildConfirmation(EmployeeId owner, EmployeeId proxy, LocalDateTime confirmationDate);

    void buildCreatedAt(LocalDateTime createdAt);

}
