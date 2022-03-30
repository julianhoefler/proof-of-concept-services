package de.abschlussprojekt.core.resolver;

import de.abschlussprojekt.core.models.DepartureBoard;
import de.abschlussprojekt.core.models.Type;
import de.abschlussprojekt.core.validators.DateValidator;
import de.abschlussprojekt.core.validators.TimeFilter;
import de.abschlussprojekt.core.validators.TrainTypeFilter;
import de.abschlussprojekt.dmadapter.FileNameResolver;
import de.abschlussprojekt.dmadapter.repositories.DepartureBoardRepository;
import de.abschlussprojekt.dmadapter.repositories.JourneyDetailRepository;

import javax.inject.Inject;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class Resolver {

    @Inject
    DateValidator dateValidator;

    @Inject
    LocationIdResolver locationIdResolver;

    @Inject
    TimeFilter timeFilter;

    @Inject
    TrainTypeFilter trainTypeFilter;

    @Inject
    MapResolver mapResolver;

    @Inject
    FileNameResolver fileNameResolver;

    @Inject
    DepartureBoardRepository departureBoardRepository;

    @Inject
    JourneyDetailRepository journeyDetailRepository;

    public List<DepartureBoard> applyFilters(
            List<DepartureBoard> departureBoardList,
            String type,
            String localDateTime
    ) {
        if (dateValidator.hasTime(localDateTime)) {
            departureBoardList = timeFilter.filterByTime(departureBoardList, LocalDateTime.parse(localDateTime));
        }

        if (type != null) {
            Type trainType = Type.valueOf(type.toUpperCase(Locale.ROOT));
            departureBoardList = trainTypeFilter.filterByTrainType(departureBoardList, trainType);
        }

        return departureBoardList;
    }

    public Integer getIdByLocationName(String searchTerm) {
        return locationIdResolver.getId(searchTerm);
    }

    public String getFileName(String locationId, LocalDate localDate) {
        return locationId + "_" + localDate.toString();
    }

    public List<DepartureBoard> resolveJourneyDetailsById(List<DepartureBoard> departureBoardList) {
        if (departureBoardList == null){
            return Collections.emptyList();
        }
        return departureBoardList.stream()
                .map(this::appendJourneyDetails)
                .collect(Collectors.toList());
    }

    public DepartureBoard appendJourneyDetails(DepartureBoard departureBoard) {
        String detailsId = departureBoard.getDetailsId();
        String fileName = fileNameResolver.resolvePath(fileNameResolver.getMd5Hash(detailsId), "journeydetails");

        if (fileNameResolver.fileExistsInJourneyDetails(detailsId)) {
            departureBoard.setJourneyDetailsList(journeyDetailRepository.getByName(fileName));
        } else {
            departureBoard.setJourneyDetailsList(Collections.emptyList());
        }

        return departureBoard;
    }
}
