package gr.aueb.dmst.simplinvoice.validator;

public class EmailExistsException extends Exception {

    public EmailExistsException(final String message) {
        super(message);
    }

}
