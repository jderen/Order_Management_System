package app.domain.foundation;

public class DomainEntityNotFoundException extends RuntimeException {

    public DomainEntityNotFoundException(String msg) {
        super(msg);
    }
}
