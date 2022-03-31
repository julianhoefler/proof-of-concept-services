package de.abschlussprojekt.api;

import de.abschlussprojekt.core.models.Reiseloesung;
import de.abschlussprojekt.core.resolver.ReiseloesungResolver;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
@AllArgsConstructor
public class ReiseloesungController {

    private final ReiseloesungResolver reiseloesungResolver;

    @GetMapping(value = "/reiseloesung", produces = MediaType.APPLICATION_JSON_VALUE)
    public Reiseloesung getResponse(@NotNull @RequestParam("abfahrtLocation") String abfahrtLocation,
                                    @NotNull @RequestParam("ankunftLocation") String ankunftLocation,
                                    @NotNull @RequestParam("hinfahrtDate") String hinfahrtDate,
                                    @Nullable @RequestParam("rueckfahrtDate") String rueckfahrtDate,
                                    @Nullable @RequestParam("trainType") String type) {

        return reiseloesungResolver.getReiseloesung(abfahrtLocation, ankunftLocation, hinfahrtDate, rueckfahrtDate, type);
    }
}
