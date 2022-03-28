package org.acme.lifecycle;

import de.abschlussprojekt.core.models.DepartureBoard;
import de.abschlussprojekt.core.models.JourneyDetails;
import de.abschlussprojekt.core.models.Location;
import de.abschlussprojekt.core.models.LocationId;
import de.abschlussprojekt.core.resolver.JsonFormatter;
import de.abschlussprojekt.dmadapter.FileNameResolver;
import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class AppLifecycleBean {

    @Inject
    JsonFormatter jsonFormatter;

    @Inject
    FileNameResolver fileNameResolver;

    private static final Logger LOGGER = Logger.getLogger("ListenerBean");

    private static List<LocationId> locationIdList = new ArrayList<>();
    private static final Map<String, List<DepartureBoard>> departureBoardMap = new HashMap<>();
    private static final Map<String, List<JourneyDetails>> journeyDetailsMap = new HashMap<>();

    void onStart(@Observes StartupEvent ev) {
        LOGGER.info("onStart Method has been triggered");
        loadLocationIds();
        loadDepartureBoards();
        loadJourneyDetails();
    }

    void onStop(@Observes ShutdownEvent ev) {
        LOGGER.info("onStop Method has been triggered");
    }

    private void loadLocationIds() {
        String filePath = fileNameResolver.resolvePath("locationids", "locationids");
        locationIdList = jsonFormatter.getFileAsList(filePath, LocationId.class);
    }

    private void loadDepartureBoards() {
        String subDirectory = "departureboard";
        List<String> filePathList = fileNameResolver.resolveAllFilePathsInSubDirectory(subDirectory);
        filePathList.forEach(filePath ->
                departureBoardMap.put(filePath, jsonFormatter.getFileAsList(filePath, DepartureBoard.class))
        );
    }

    private void loadJourneyDetails() {
        String subDirectory = "journeydetails";
        List<String> filePathList = fileNameResolver.resolveAllFilePathsInSubDirectory(subDirectory);
        filePathList.forEach(filePath -> {
            journeyDetailsMap.put(filePath, jsonFormatter.getFileAsList(filePath, JourneyDetails.class));
        });
    }

    public static Map<String, List<DepartureBoard>> getDepartureBoardList() {
        return departureBoardMap;
    }

    public static List<LocationId> getLocationIdList() {
        return locationIdList;
    }

    public static Map<String, List<JourneyDetails>> getJourneyDetailsMap() {
        LOGGER.info("getLocationList has been called");
        return journeyDetailsMap;
    }
}