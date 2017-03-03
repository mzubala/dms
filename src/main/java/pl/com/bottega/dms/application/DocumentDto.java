package pl.com.bottega.dms.application;

import java.util.List;

public class DocumentDto {
    private String title;
    private String number;

    private String content;

    private String status;
    private List<ConfirmationDto> confirmations;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ConfirmationDto> getConfirmations() {
        return confirmations;
    }

    public void setConfirmations(List<ConfirmationDto> confirmations) {
        this.confirmations = confirmations;
    }
}
