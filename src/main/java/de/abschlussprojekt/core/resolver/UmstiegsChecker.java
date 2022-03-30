package de.abschlussprojekt.core.resolver;

import de.abschlussprojekt.core.models.DepartureBoard;
import de.abschlussprojekt.core.models.JourneyDetails;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.LocalDate;
import java.util.Collection;
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
            List<JourneyDetails> journeyDetailsList = getJourneyDetailsListFromStopId(stopId, date, type);

            for (int index = 0; index < journeyDetailsList.size(); index++) {
                List<List<JourneyDetails>> verbindung = getVerbindungWhenStopIdEqualsAnkunft(journeyDetailsList, locationIdAbfahrt, locationIdAnkunft, date, type, stopId, index);

                if (verbindung != null) {
                    return verbindung;
                }
            }
        }
        return Collections.emptyList();
    }

    private List<JourneyDetails> getJourneyDetailsListFromStopId(Integer stopId, String date,
                                                                 String type) {
        String fileName = reiseloesungResolver.getFileName(stopId.toString(), LocalDate.parse(date));

        return reiseloesungResolver.getFilterAndResolveDepartureBoardList(fileName, date, type)
                .stream()
                .map(DepartureBoard::getJourneyDetailsList)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    private List<List<JourneyDetails>> getVerbindungWhenStopIdEqualsAnkunft(List<JourneyDetails> journeyDetailsList, Integer locationIdAbfahrt,
                                                                            Integer locationIdAnkunft,
                                                                            String date,
                                                                            String type,
                                                                            Integer stopId,
                                                                            int index) {
        if (journeyDetailsList.get(index).getStopdId().equals(locationIdAnkunft)) {
            return getListOfVerbindungen(journeyDetailsList, locationIdAbfahrt, locationIdAnkunft, date, type, stopId);
        }

        return null;
    }

    private List<List<JourneyDetails>> getListOfVerbindungen(List<JourneyDetails> journeyDetailsList,
                                                             Integer locationIdAbfahrt,
                                                             Integer locationIdAnkunft,
                                                             String date,
                                                             String type,
                                                             Integer stopId) {
        return List.of(
                reiseloesungResolver.getJourneyDetailsList(
                        locationIdAbfahrt,
                        stopId,
                        date,
                        type
                ),
                reiseloesungResolver.cutJourneyDetailsNotNeededForReiseloesung(
                        journeyDetailsList,
                        stopId,
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
