package de.abschlussprojekt.dmadapter.repositories;

import de.abschlussprojekt.core.models.JourneyDetails;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class JourneyDetailRepository extends AbstractRepository<JourneyDetails> {

    public JourneyDetailRepository() {
        super(JourneyDetails.class);
    }

    public String getSubDirectory() {
        return "journeydetails";
    }
}
