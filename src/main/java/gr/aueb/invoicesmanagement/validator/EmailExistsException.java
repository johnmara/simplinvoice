package gr.aueb.invoicesmanagement.validator;

public class EmailExistsException extends Exception {

    public EmailExistsException(final String message) {
        super(message);
    }

}
