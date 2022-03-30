package de.abschlussprojekt.core.resolver;

import de.abschlussprojekt.core.models.LocationId;
import org.acme.lifecycle.AppLifecycleBean;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class LocationIdResolver {

    public Integer getId(String searchTerm) {
        List<LocationId> locationIdList = AppLifecycleBean.getLocationIdList();

        Optional<LocationId> locationIdOptional = locationIdList.stream()
                .filter(locationId -> locationId.getName().equalsIgnoreCase(searchTerm))
                .distinct()
                .findFirst();

        return locationIdOptional.orElseThrow().getId();
    }
}
