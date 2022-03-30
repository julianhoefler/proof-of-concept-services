package de.abschlussprojekt.core.resolver;

import de.abschlussprojekt.core.models.DepartureBoard;
import de.abschlussprojekt.core.models.JourneyDetails;
import de.abschlussprojekt.core.models.Reiseloesung;

import javax.enterprise.context.ApplicationScoped;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class ReiseloesungResolver extends Resolver {

    public Reiseloesung getReiseloesung(
            String abfahrtLocation,
            String ankunftLocation,
            String hinfahrtDate,
            String rueckfahrtDate,
            String type
    ) {
        Integer locationIdAbfahrt = getIdByLocationName(abfahrtLocation);
        Integer locationIdAnkunft = getIdByLocationName(ankunftLocation);

        return Reiseloesung.builder()
                .hinfahrt(getEinfacheReiseloesung(locationIdAbfahrt, locationIdAnkunft, hinfahrtDate, type))
                .rueckfahrt(determineRueckfahrt(rueckfahrtDate, type, locationIdAbfahrt, locationIdAnkunft))
                .build();
    }

    private List<List<JourneyDetails>> determineRueckfahrt(String rueckfahrtDate,
                                                           String type,
                                                           Integer locationIdAbfahrt,
                                                           Integer locationIdAnkunft) {
        if (rueckfahrtDate != null) {
            return getEinfacheReiseloesung(locationIdAnkunft, locationIdAbfahrt, rueckfahrtDate, type);
        }
        return null;
    }

    private List<List<JourneyDetails>> getEinfacheReiseloesung(Integer locationIdAbfahrt,
                                                               Integer locationIdAnkunft,
                                                               String date,
                                                               String type) {

        List<List<JourneyDetails>> einfacheReiseloesung;

        //TODO: herausfinden, wo Exception fliegt und try/catch entfernen
        try {
            einfacheReiseloesung = List.of(getJourneyDetailsList(locationIdAbfahrt, locationIdAnkunft, date, type));
        } catch (Exception e) {
            einfacheReiseloesung = checkUmstiegsverbindung(
                    getFilterAndResolveDepartureBoardList(getFileName(locationIdAbfahrt.toString(), LocalDate.parse(date)), date, type),
                    locationIdAbfahrt,
                    locationIdAnkunft,
                    date,
                    type
            );
        }
        return einfacheReiseloesung;
    }

    private List<JourneyDetails> getJourneyDetailsList(
            Integer locationIdAbfahrt,
            Integer locationIdAnkunft,
            String date,
            String type
    ) {
        LocalDateTime localDateTime = dateValidator.mapToLocalDateTime(date);
        dateValidator.validate(localDateTime);

        String fileName = getFileName(locationIdAbfahrt.toString(), localDateTime.toLocalDate());
        List<DepartureBoard> departureBoardListWithJourneyDetails = getFilterAndResolveDepartureBoardList(fileName, date, type);
        List<JourneyDetails> journeyDetailsListFilteredyByMatchingLocationdIds = filterByMatchingLocationIds(departureBoardListWithJourneyDetails, locationIdAbfahrt, locationIdAnkunft);

        return cutJourneyDetailsNotNeededForReiseloesung(journeyDetailsListFilteredyByMatchingLocationdIds, locationIdAbfahrt, locationIdAnkunft);
    }

    private List<List<JourneyDetails>> checkUmstiegsverbindung(List<DepartureBoard> departureBoardList,
                                                               Integer locationIdAbfahrt,
                                                               Integer locationIdAnkunft,
                                                               String date,
                                                               String type) {
        List<Integer> stopIdList = departureBoardList.stream()
                .flatMap(departureBoard -> departureBoard.getJourneyDetailsList().stream())
                .map(JourneyDetails::getStopdId)
                .collect(Collectors.toList());

        for (Integer stopId : stopIdList) {
            List<DepartureBoard> currentDepartureBoardList;

            try {
                currentDepartureBoardList = getFilterAndResolveDepartureBoardList(getFileName(stopId.toString(), LocalDate.parse(date)), date, type);
                currentDepartureBoardList.stream()
                        .filter(departureBoard -> departureBoard.getJourneyDetailsList().stream()
                                .anyMatch(journeyDetails -> journeyDetails.getStopdId().equals(locationIdAnkunft)));
            } catch (NullPointerException e) {
                continue;
            }

            if (!currentDepartureBoardList.isEmpty()) {
                for (DepartureBoard departureBoard : currentDepartureBoardList) {
                    for (int j = 0; j <= departureBoard.getJourneyDetailsList().size(); j++) {
                        try {
                            if (departureBoard.getJourneyDetailsList().get(j).getStopdId().equals(locationIdAnkunft)) {
                                return List.of(
                                        getJourneyDetailsList(
                                                locationIdAbfahrt,
                                                departureBoard.getStopId(),
                                                date,
                                                type
                                        ),
                                        cutJourneyDetailsNotNeededForReiseloesung(
                                                departureBoard.getJourneyDetailsList(),
                                                departureBoard.getStopId(),
                                                locationIdAnkunft
                                        )
                                );
                            }
                        } catch (IndexOutOfBoundsException ignored) {
                        }
                    }
                }
            }
        }
        return Collections.emptyList();
    }

    private List<DepartureBoard> getFilterAndResolveDepartureBoardList(
            String fileName,
            String date,
            String type
    ) {
        fileName = fileNameResolver.resolvePath(fileName, "departureboard");
        List<DepartureBoard> departureBoardListFiltered = applyFilters(departureBoardRepository.getByName(fileName), type, date);
        return resolveJourneyDetailsById(departureBoardListFiltered);
    }

    private List<JourneyDetails> cutJourneyDetailsNotNeededForReiseloesung(
            List<JourneyDetails> journeyDetailsList,
            Integer locationIdAbfahrt,
            Integer locationIdAnkunft
    ) {
        int indexAbfahrtId = -1;
        int indexAnkunftId = -1;

        List<JourneyDetails> newJourneyDetailsList = new ArrayList<>();

        for (int i = 0; i < journeyDetailsList.size(); i++) {
            if (journeyDetailsList.get(i).getStopdId().equals(locationIdAbfahrt)) {
                indexAbfahrtId = i;
            }
            if (journeyDetailsList.get(i).getStopdId().equals(locationIdAnkunft)) {
                indexAnkunftId = i;
            }
        }

        for (int j = indexAbfahrtId; j <= indexAnkunftId; j++) {
            newJourneyDetailsList.add(journeyDetailsList.get(j));
        }

        return newJourneyDetailsList;
    }

    private List<JourneyDetails> filterByMatchingLocationIds(
            List<DepartureBoard> departureBoardList,
            Integer locationIdAbfahrt,
            Integer locationIdAnkunft
    ) {
        return departureBoardList.stream()
                .filter(departureBoard -> !departureBoard.getJourneyDetailsList().isEmpty())
                .filter(departureBoard -> departureBoard.getJourneyDetailsList().stream()
                        .map(JourneyDetails::getStopdId)
                        .anyMatch(stopId -> stopId.equals(locationIdAbfahrt)))
                .filter(departureBoard -> departureBoard.getJourneyDetailsList().stream()
                        .map(JourneyDetails::getStopdId)
                        .anyMatch(stopId -> stopId.equals(locationIdAnkunft)))
                .filter(departureBoard -> abfahrtBeforeAnkunft(departureBoard.getJourneyDetailsList(), locationIdAbfahrt, locationIdAnkunft))
                .findFirst()
                .orElse(new DepartureBoard())
                .getJourneyDetailsList();
    }

    private boolean abfahrtBeforeAnkunft(
            List<JourneyDetails> journeyDetailsList,
            Integer locationIdAbfahrt,
            Integer locationIdAnkunft
    ) {
        int indexLocationIdAbfahrt = -1;
        int indexLocationIdAnkunft = -2;

        for (int i = 0; i < journeyDetailsList.size(); i++) {

            Integer currentStopId = journeyDetailsList.get(i).getStopdId();

            if (currentStopId.equals(locationIdAbfahrt)) {
                indexLocationIdAbfahrt = i;
            }
            if (currentStopId.equals(locationIdAnkunft)) {
                indexLocationIdAnkunft = i;
            }
        }

        return indexLocationIdAbfahrt < indexLocationIdAnkunft;
    }
}
