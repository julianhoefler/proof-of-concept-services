package de.abschlussprojekt.api;

import de.abschlussprojekt.core.models.Reiseloesung;
import de.abschlussprojekt.core.resolver.ReiseloesungResolver;
import io.smallrye.common.constraint.NotNull;
import lombok.AllArgsConstructor;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@AllArgsConstructor
@Path("/reiseloesung")
public class ReiseloesungController {

    /**
     * Instanz zum Erstellen von Reiseloesungen, wird durch Dependency Injection instanziiert.
     */
    private final ReiseloesungResolver reiseloesungResolver;

    /**
     * Methode zum Erstellen einer Reiseloesung, benoetigt mindestens abfahrtLocation, ankunftLocation und hinfahrtDate.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.MEDIA_TYPE_WILDCARD)
    public Reiseloesung getResponse(@NotNull @QueryParam("abfahrtLocation") String abfahrtLocation,
                                    @NotNull @QueryParam("ankunftLocation") String ankunftLocation,
                                    @NotNull @QueryParam("hinfahrtDate") String hinfahrtDate,
                                    @QueryParam("rueckfahrtDate") String rueckfahrtDate,
                                    @QueryParam("trainType") String type) {

        return reiseloesungResolver.getReiseloesung(abfahrtLocation, ankunftLocation, hinfahrtDate, rueckfahrtDate, type);
    }
}
