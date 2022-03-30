package de.abschlussprojekt.dmadapter.repositories;

import de.abschlussprojekt.core.models.JourneyDetails;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class JourneyDetailsRepository extends AbstractRepository<JourneyDetails> {

    public JourneyDetailsRepository() {
        super(JourneyDetails.class);
    }

    public String getSubDirectory() {
        return "journeydetails";
    }

    public void getJourneyDetailsMap() {
        String path = fileNameResolver.resolvePath("journeydetails", "journeydetails");
        List<JourneyDetails> allJourneyDetails = jsonFormatter.getFileAsList(path, JourneyDetails.class);

        this.data = allJourneyDetails.stream()
                .collect(Collectors.groupingBy(JourneyDetails::getId));
    }
}
