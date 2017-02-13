package pl.com.bottega.dms.model.commands;

public class ChangeDocumentCommand {
    private String title;
    private String content;

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
