package de.abschlussprojekt.core.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JourneyDetails {

    @JsonProperty("stopId")
    Integer stopdId;

    @JsonProperty("stopName")
    String stopName;

    @JsonProperty("lat")
    String lat;

    @JsonProperty("lon")
    String lon;

    @JsonProperty("depTime")
    String depTime;

    @JsonProperty("arrTime")
    String arrTime;

    @JsonProperty("train")
    String train;

    @JsonProperty("type")
    Type type;

    @JsonProperty("operator")
    Operator operator;

    @JsonProperty("notes")
    List<Note> notes;
}
