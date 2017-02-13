package pl.com.bottega.dms.application;

import pl.com.bottega.dms.model.commands.ConfirmDocumentCommand;
import pl.com.bottega.dms.model.commands.ConfirmForDocumentCommand;

public interface ReadingConfirmator {

    void confirm(ConfirmDocumentCommand cmd);

    void confirmFor(ConfirmForDocumentCommand cmd);

}
