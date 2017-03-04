package pl.com.bottega.dms.application;

import java.util.List;

public class DocumentSearchResults {

    private List<DocumentDto> documents;
    private Integer pageNumber;
    private Integer perPage;
    private Long pagesCount;

    public List<DocumentDto> getDocuments() {
        return documents;
    }

    public void setDocuments(List<DocumentDto> documents) {
        this.documents = documents;
    }

    public Long getPagesCount() {
        return pagesCount;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPerPage() {
        return perPage;
    }

    public void setPerPage(Integer perPage) {
        this.perPage = perPage;
    }

    public void setPagesCount(Long pagesCount) {
        this.pagesCount = pagesCount;
    }
}
