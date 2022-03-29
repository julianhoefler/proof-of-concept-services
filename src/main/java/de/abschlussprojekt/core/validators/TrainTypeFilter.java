package de.abschlussprojekt.core.validators;

import de.abschlussprojekt.core.models.DepartureBoard;
import de.abschlussprojekt.core.models.Type;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class TrainTypeFilter {

    public List<DepartureBoard> filterByTrainType(List<DepartureBoard> departureBoardList, Type type){
        return departureBoardList.stream()
                .filter(departureBoard -> departureBoard.getType() == type)
                .collect(Collectors.toList());
    }
}
