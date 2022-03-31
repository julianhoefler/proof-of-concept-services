package de.abschlussprojekt.core.validators;

import de.abschlussprojekt.core.models.DepartureBoard;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class TimeFilterTest {

    @Autowired
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