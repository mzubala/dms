package pl.com.bottega.dms.model.commands;

public class CommandInvalidException extends RuntimeException {

    private Validatable.ValidationErrors errors;

    public Validatable.ValidationErrors getErrors() {
        return errors;
    }

    public CommandInvalidException(Validatable.ValidationErrors errors) {
        this.errors = errors;
    }

}
