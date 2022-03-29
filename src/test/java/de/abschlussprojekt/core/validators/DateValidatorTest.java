package de.abschlussprojekt.core.validators;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import java.time.LocalDateTime;

@QuarkusTest
class DateValidatorTest {

    @Inject
    DateValidator dateValidator;

    private final LocalDateTime invalidLocalDateTime = LocalDateTime.parse("2022-01-01T00:00");
    private final String validLocalDateTime = "2022-08-01T00:00";
    private final String validLocalDate = "2022-08-01";

    @Test
    void validate_shouldThrowException_whenLocalDateTimeIsNotInTheFuture() {
        Assertions.assertThrows(BadRequestException.class, () -> dateValidator.validate(invalidLocalDateTime));
    }

    @Test
    void mapToLocalDateTime_shouldReturnLocalDateTime_whenStringWithTimeIsGiven() {
        LocalDateTime result = dateValidator.mapToLocalDateTime(validLocalDateTime);

        Assertions.assertEquals(result, LocalDateTime.of(2022, 8, 1, 0, 0));
    }

    @Test
    void mapToLocalDateTime_shouldReturnLocalDateTime_whenStringWithTimeIsNotGiven() {
        LocalDateTime result = dateValidator.mapToLocalDateTime(validLocalDate);

        Assertions.assertEquals(result, LocalDateTime.of(2022, 8, 1, 0, 0));
    }

    @Test
    void hasTime_shouldReturnTrue_whenLocalDateTimeStringHasTime() {
        boolean result = dateValidator.hasTime(validLocalDateTime);

        Assertions.assertTrue(result);
    }

    @Test
    void hasTime_shouldReturnFalse_whenLocalDateTimeStringHasNoTime() {
        boolean result = dateValidator.hasTime(validLocalDate);

        Assertions.assertFalse(result);
    }
}