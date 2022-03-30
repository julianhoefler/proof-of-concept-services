package de.abschlussprojekt.core.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Location {

    @JsonProperty("name")
    String name;

    @JsonProperty("lon")
    double lon;

    @JsonProperty("lat")
    double lat;

    @JsonProperty("id")
    int id;
}
