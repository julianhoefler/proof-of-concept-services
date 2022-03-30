package org.acme.lifecycle;

import de.abschlussprojekt.core.models.LocationId;
import de.abschlussprojekt.core.resolver.JsonFormatter;
import de.abschlussprojekt.dmadapter.FileNameResolver;
import de.abschlussprojekt.dmadapter.controller.FileLoader;
import de.abschlussprojekt.dmadapter.repositories.DepartureBoardRepository;
import de.abschlussprojekt.dmadapter.repositories.JourneyDetailsRepository;
import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class AppLifecycleBean {

    @Inject
    JsonFormatter jsonFormatter;

    @Inject
    FileNameResolver fileNameResolver;

    @Inject
    FileLoader fileLoader;

    @Inject
    DepartureBoardRepository departureBoardRepository;

    @Inject
    JourneyDetailsRepository journeyDetailsRepository;

    private static final Logger LOGGER = Logger.getLogger("ListenerBean");

    private static List<LocationId> locationIdList = new ArrayList<>();

    void onStart(@Observes StartupEvent ev) {
        LOGGER.info("onStart Method has been triggered");
        loadLocationIds();
        departureBoardRepository.getDepartureBoardMap();
        journeyDetailsRepository.getJourneyDetailsMap();
    }

    void onStop(@Observes ShutdownEvent ev) {
        LOGGER.info("onStop Method has been triggered");
    }

    private void loadLocationIds() {
        String filePath = fileNameResolver.resolvePath("locationids", "locationids");
        locationIdList = jsonFormatter.getFileAsList(filePath, LocationId.class);
    }

    public static List<LocationId> getLocationIdList() {
        return locationIdList;
    }
}