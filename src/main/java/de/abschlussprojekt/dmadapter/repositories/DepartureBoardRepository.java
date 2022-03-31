package de.abschlussprojekt.dmadapter.repositories;

import de.abschlussprojekt.core.models.DepartureBoard;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class DepartureBoardRepository extends AbstractRepository<DepartureBoard> {

    public DepartureBoardRepository() {
        super(DepartureBoard.class);
    }

    public String getSubDirectory() {
        return "departureboard";
    }

    public void getDepartureBoardMap() {
        String path = fileNameResolver.resolvePath("departureboard", "departureboard");
        List<DepartureBoard> allDepartureBoards = jsonFormatter.getFileAsList(path, DepartureBoard.class);

        this.data = allDepartureBoards.stream()
                .collect(Collectors.groupingBy(departureBoard -> {
                    return departureBoard.getBoardId().toString() +
                            "_" +
                            LocalDateTime.parse(departureBoard.getDateTime()).toLocalDate().toString();
                }));
    }
}
