package de.abschlussprojekt.core.validators;

import de.abschlussprojekt.core.models.DepartureBoard;
import de.abschlussprojekt.core.models.Type;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TrainTypeFilter {

    public List<DepartureBoard> filterByTrainType(List<DepartureBoard> departureBoardList, Type type){
        if (departureBoardList == null){
            return Collections.emptyList();
        }
        return departureBoardList.stream()
                .filter(departureBoard -> departureBoard.getType() == type)
                .collect(Collectors.toList());
    }
}
