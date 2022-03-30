package de.abschlussprojekt.core.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class Reiseloesung {

    List<List<JourneyDetails>> hinfahrt;

    List<List<JourneyDetails>> rueckfahrt;
}
