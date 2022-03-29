package de.abschlussprojekt.core.validators;

import de.abschlussprojekt.core.models.DepartureBoard;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class TimeFilterTest {

    @Inject
    TimeFilter timeFilter;

    List<DepartureBoard> departureBoardList = new ArrayList<>();

    DepartureBoard departureBoard1 = DepartureBoard.builder()
            .dateTime("2022-08-01T22:00")
            .build();

    DepartureBoard departureBoard2 = DepartureBoard.builder()
            .dateTime("2022-08-01T08:00")
            .build();

    @BeforeEach
    void setUp() {
        departureBoardList.add(departureBoard1);
        departureBoardList.add(departureBoard2);
    }

    @Test
    void filterByTime_shouldReturnCorrectDepartureBoardList() {
        List<DepartureBoard> result = timeFilter.filterByTime(departureBoardList, LocalDateTime.parse("2022-08-01T12:00"));
        List<DepartureBoard> expected = List.of(departureBoard1);

        Assertions.assertEquals(result, expected);
    }
}