package de.abschlussprojekt.core.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Reiseloesung {

    List<List<JourneyDetails>> hinfahrt;

    List<List<JourneyDetails>> rueckfahrt;
}
