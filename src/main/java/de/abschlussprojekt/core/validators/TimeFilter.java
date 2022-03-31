package de.abschlussprojekt.core.validators;

import de.abschlussprojekt.core.models.DepartureBoard;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TimeFilter {

    public List<DepartureBoard> filterByTime(List<DepartureBoard> departureBoardList, LocalDateTime time) {
        return departureBoardList.stream()
                .filter(departureBoard ->
                {
                    LocalDateTime localDateTime = LocalDateTime.parse(departureBoard.getDateTime());
                    return localDateTime.isAfter(LocalDateTime.parse(time.toString()));
                })
                .collect(Collectors.toList());
    }
}
