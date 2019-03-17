package validator;

/**
 * the validation exception class
 */
public class ValidationException extends RuntimeException {
    /**
     * Constructor
     * @param err - the message error
     */
    public ValidationException(String err){
        super(err);
    }
}