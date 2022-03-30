package de.abschlussprojekt.core.resolver;

import de.abschlussprojekt.core.models.DepartureBoard;
import de.abschlussprojekt.core.models.JourneyDetails;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class UmstiegsChecker {

    @Inject
    ReiseloesungResolver reiseloesungResolver;

    public List<List<JourneyDetails>> checkUmstiegsverbindung(List<DepartureBoard> departureBoardList,
                                                              Integer locationIdAbfahrt,
                                                              Integer locationIdAnkunft,
                                                              String date,
                                                              String type) {

        List<Integer> stopIdList = getStopIdList(departureBoardList);

        for (Integer stopId : stopIdList) {
            List<DepartureBoard> currentDepartureBoardList;

            currentDepartureBoardList = reiseloesungResolver.getFilterAndResolveDepartureBoardList(reiseloesungResolver.getFileName(stopId.toString(), LocalDate.parse(date)), date, type);

            for (DepartureBoard departureBoard : currentDepartureBoardList) {
                for (int j = 0; j < departureBoard.getJourneyDetailsList().size(); j++) {
                    List<List<JourneyDetails>> verbindung = getVerbindungWhenStopIdEqualsAnkunft(locationIdAbfahrt, locationIdAnkunft, date, type, departureBoard, j);
                    if (verbindung != null) {
                        return verbindung;
                    }
                }
            }
        }
        return Collections.emptyList();
    }

    private List<List<JourneyDetails>> getVerbindungWhenStopIdEqualsAnkunft(Integer locationIdAbfahrt, Integer locationIdAnkunft, String date, String type, DepartureBoard departureBoard, int index) {
        if (departureBoard.getJourneyDetailsList().get(index).getStopdId().equals(locationIdAnkunft)) {
            return getListOfVerbindungen(locationIdAbfahrt, locationIdAnkunft, date, type, departureBoard);
        }

        return null;
    }

    private List<List<JourneyDetails>> getListOfVerbindungen(Integer locationIdAbfahrt, Integer locationIdAnkunft, String date, String type, DepartureBoard departureBoard) {
        return List.of(
                reiseloesungResolver.getJourneyDetailsList(
                        locationIdAbfahrt,
                        departureBoard.getStopId(),
                        date,
                        type
                ),
                reiseloesungResolver.cutJourneyDetailsNotNeededForReiseloesung(
                        departureBoard.getJourneyDetailsList(),
                        departureBoard.getStopId(),
                        locationIdAnkunft
                )
        );
    }

    private List<Integer> getStopIdList(List<DepartureBoard> departureBoardList) {
        return departureBoardList.stream()
                .flatMap(departureBoard -> departureBoard.getJourneyDetailsList().stream())
                .map(JourneyDetails::getStopdId)
                .collect(Collectors.toList());
    }
}
