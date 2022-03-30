package de.abschlussprojekt.core.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.arc.All;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class DepartureBoard {

    @JsonProperty("name")
    String name;

    @JsonProperty("type")
    Type type;

    @JsonProperty("boardId")
    Integer boardId;

    @JsonProperty("stopId")
    Integer stopId;

    @JsonProperty("stopName")
    String stopName;

    @JsonProperty("dateTime")
    String dateTime;

    @JsonProperty("origin")
    String origin;

    @JsonProperty("track")
    String track;

    @JsonProperty("detailsId")
    String detailsId;

    List<JourneyDetails> journeyDetailsList;
}
