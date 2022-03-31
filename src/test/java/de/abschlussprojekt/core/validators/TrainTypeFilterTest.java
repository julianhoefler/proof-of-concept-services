package de.abschlussprojekt.core.validators;

import de.abschlussprojekt.core.models.DepartureBoard;
import de.abschlussprojekt.core.models.Type;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class TrainTypeFilterTest {

    @Autowired
    TrainTypeFilter trainTypeFilter;

    List<DepartureBoard> departureBoardList = new ArrayList<>();
    DepartureBoard departureBoard1 = DepartureBoard.builder()
            .type(Type.NJ)
            .build();
    DepartureBoard departureBoard2 = DepartureBoard.builder()
            .type(Type.ICE)
            .build();


    @BeforeEach
    void setUp() {
        departureBoardList.add(departureBoard1);
        departureBoardList.add(departureBoard2);
    }

    @Test
    void filterByTrainType_shouldReturnCorrectDepartureBoardList() {
        List<DepartureBoard> result = trainTypeFilter.filterByTrainType(departureBoardList, Type.ICE);
        List<DepartureBoard> expected = List.of(departureBoard2);

        Assertions.assertEquals(result, expected);
    }
}