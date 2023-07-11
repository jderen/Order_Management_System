package app.domain.foundation;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class DomainEntityNotFoundExceptionTest {

    private static final String EXCEPTION_MESSAGE = "test message";

    @Test
    void shouldCreateExceptionWithMessage() {
        // when
        DomainEntityNotFoundException exception = new DomainEntityNotFoundException(EXCEPTION_MESSAGE);

        // then
        assertNotNull(exception);
        assertNotNull(exception.getMessage());
        assertThat(exception.getMessage(), is(EXCEPTION_MESSAGE));
    }
}