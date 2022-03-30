package de.abschlussprojekt.core.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Note {

    @JsonProperty("key")
    String key;

    @JsonProperty("priority")
    String priority;

    @JsonProperty("text")
    String text;
}
