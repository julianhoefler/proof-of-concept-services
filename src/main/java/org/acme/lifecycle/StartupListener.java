package org.acme.lifecycle;

import de.abschlussprojekt.core.models.LocationId;
import de.abschlussprojekt.core.resolver.JsonFormatter;
import de.abschlussprojekt.dmadapter.FileNameResolver;
import de.abschlussprojekt.dmadapter.controller.FileLoader;
import de.abschlussprojekt.dmadapter.repositories.DepartureBoardRepository;
import de.abschlussprojekt.dmadapter.repositories.JourneyDetailsRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Component
public class StartupListener {

    @Autowired
    JsonFormatter jsonFormatter;

    @Autowired
    FileNameResolver fileNameResolver;

    @Autowired
    FileLoader fileLoader;

    @Autowired
    DepartureBoardRepository departureBoardRepository;

    @Autowired
    JourneyDetailsRepository journeyDetailsRepository;

    private static final Logger LOGGER = Logger.getLogger("StartupListener");

    private static final org.slf4j.Logger LOGGER1 = LoggerFactory.getLogger(StartupListener.class);

    private static List<LocationId> locationIdList = new ArrayList<>();

    @PostConstruct
    public void onStart() {
        LOGGER.info("onStart Method has been triggered");
        loadLocationIds();
        departureBoardRepository.getDepartureBoardMap();
        journeyDetailsRepository.getJourneyDetailsMap();
    }

    @PreDestroy
    void onStop() {
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