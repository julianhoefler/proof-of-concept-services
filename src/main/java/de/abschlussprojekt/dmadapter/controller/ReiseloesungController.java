package de.abschlussprojekt.dmadapter.controller;

import de.abschlussprojekt.core.resolver.ReiseloesungResolver;
import io.smallrye.common.constraint.NotNull;
import lombok.AllArgsConstructor;
import org.jboss.resteasy.reactive.RestQuery;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@AllArgsConstructor
@Path("/reiseloesung")
public class ReiseloesungController {

    private final ReiseloesungResolver reiseloesungResolver;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.MEDIA_TYPE_WILDCARD)
    public Response getResponse(@NotNull @RestQuery("abfahrtLocation") String abfahrtLocation,
                                @NotNull @RestQuery("ankunftLocation") String ankunftLocation,
                                @NotNull @RestQuery("hinfahrtDate") String hinfahrtDate,
                                @RestQuery("rueckfahrtDate") String rueckfahrtDate,
                                @RestQuery("trainType") String type) {

        return Response
                .ok(reiseloesungResolver.getReiseloesungAsString(abfahrtLocation, ankunftLocation, hinfahrtDate, rueckfahrtDate, type))
                .build();
    }
}
