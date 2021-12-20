package movie.exception;

public class InvalidScreeningException extends RuntimeException {
    public InvalidScreeningException() {
        super();
    }

    public InvalidScreeningException(String message) {
        super(message);
    }

    public InvalidScreeningException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidScreeningException(Throwable cause) {
        super(cause);
    }
}
