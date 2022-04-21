package de.abschlussprojekt.core.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@RegisterForReflection
public class LocationId {

    @JsonProperty("id")
    Integer id;

    @JsonProperty("name")
    String name;
}
