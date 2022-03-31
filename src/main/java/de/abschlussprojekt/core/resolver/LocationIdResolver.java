package de.abschlussprojekt.core.resolver;

import de.abschlussprojekt.core.models.LocationId;
import org.acme.lifecycle.StartupListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LocationIdResolver {

    public Integer getId(String searchTerm) {
        List<LocationId> locationIdList = StartupListener.getLocationIdList();

        Optional<LocationId> locationIdOptional = locationIdList.stream()
                .filter(locationId -> locationId.getName().equalsIgnoreCase(searchTerm))
                .distinct()
                .findFirst();

        return locationIdOptional.orElseThrow().getId();
    }
}
