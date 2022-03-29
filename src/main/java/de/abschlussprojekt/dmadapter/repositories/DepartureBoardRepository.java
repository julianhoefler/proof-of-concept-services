package de.abschlussprojekt.dmadapter.repositories;

import de.abschlussprojekt.core.models.DepartureBoard;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DepartureBoardRepository extends AbstractRepository<DepartureBoard> {

    public DepartureBoardRepository() {
        super(DepartureBoard.class);
    }

    public String getSubDirectory() {
        return "departureboard";
    }
}
