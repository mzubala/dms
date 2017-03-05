package pl.com.bottega.dms.infrastructure;

import com.sun.deploy.util.StringUtils;
import org.springframework.stereotype.Component;
import pl.com.bottega.dms.application.*;
import pl.com.bottega.dms.model.Confirmation;
import pl.com.bottega.dms.model.Document;
import pl.com.bottega.dms.model.DocumentNumber;
import pl.com.bottega.dms.model.DocumentStatus;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Component
public class JPQLDocumentCatalog implements DocumentCatalog {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public DocumentSearchResults find(DocumentQuery documentQuery) {
        DocumentSearchResults results = new DocumentSearchResults();

        String jpqlQuery = "SELECT d FROM Document d LEFT JOIN FETCH d.confirmations ";
        Set<String> predicates = createPredicates(documentQuery);
        if(!predicates.isEmpty()) {
            String where = " WHERE " + StringUtils.join(predicates, " AND ");
            jpqlQuery += where;
        }

        Query query = entityManager.createQuery(jpqlQuery);
        applyPredicateParameters(query, documentQuery);
        query.setFirstResult(0);
        query.setMaxResults(documentQuery.getPerPage());
        List<Document> documents = query.getResultList();
        List<DocumentDto> dtos = new LinkedList<>();
        for (Document document : documents)
            dtos.add(createDocumentDto(document));
        results.setDocuments(dtos);
        return results;
    }

    private void applyPredicateParameters(Query query, DocumentQuery documentQuery) {
        if(documentQuery.getStatus() != null) {
            query.setParameter("status", DocumentStatus.valueOf(documentQuery.getStatus()));
        }
        if(documentQuery.getCreatorId() != null) {
            query.setParameter("id", documentQuery.getCreatorId());
        }
        if(documentQuery.getPhrase() != null) {
            String likeExpression = "%" + documentQuery.getPhrase() + "%";
            query.setParameter("likeExpression", likeExpression);
        }
    }

    private Set<String> createPredicates(DocumentQuery documentQuery) {
        Set<String> predicates = new HashSet<>();
        if(documentQuery.getStatus() != null) {
            predicates.add("d.status = :status");
        }
        if(documentQuery.getCreatorId() != null) {
            predicates.add("d.creatorId.id = :id");
        }
        if(documentQuery.getPhrase() != null) {
            predicates.add("(d.number.number LIKE :likeExpression OR d.content LIKE :likeExpression OR d.title LIKE :likeExpression)");
        }
        return predicates;
    }

    @Override
    public DocumentDto get(DocumentNumber documentNumber) {
        Query query = entityManager.createQuery("FROM Document d LEFT JOIN FETCH d.confirmations WHERE d.number = :nr");
        query.setParameter("nr", documentNumber);
        Document document = (Document) query.getResultList().get(0);
        DocumentDto documentDto = createDocumentDto(document);
        return documentDto;
    }

    private DocumentDto createDocumentDto(Document document) {
        DocumentDto documentDto = new DocumentDto();
        documentDto.setNumber(document.getNumber().getNumber());
        documentDto.setTitle(document.getTitle());
        documentDto.setContent(document.getContent());
        documentDto.setStatus(document.getStatus().name());
        List<ConfirmationDto> confirmationDtos = new LinkedList<>();
        for (Confirmation confirmation : document.getConfirmations()) {
            ConfirmationDto dto = createConfirmationDto(confirmation);
            confirmationDtos.add(dto);
        }
        documentDto.setConfirmations(confirmationDtos);
        return documentDto;
    }

    private ConfirmationDto createConfirmationDto(Confirmation confirmation) {
        ConfirmationDto dto = new ConfirmationDto();
        dto.setConfirmed(confirmation.isConfirmed());
        dto.setConfirmedAt(confirmation.getConfirmationDate());
        dto.setOwnerEmployeeId(confirmation.getOwner().getId());
        if (confirmation.hasProxy())
            dto.setProxyEmployeeId(confirmation.getProxy().getId());
        return dto;
    }
}
