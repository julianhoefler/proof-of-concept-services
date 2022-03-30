package de.abschlussprojekt.core.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
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
