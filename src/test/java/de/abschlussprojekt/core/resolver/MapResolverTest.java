package de.abschlussprojekt.core.resolver;

import de.abschlussprojekt.core.models.JourneyDetails;
import de.abschlussprojekt.dmadapter.FileNameResolver;
import io.quarkus.test.junit.QuarkusMock;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@QuarkusTest
class MapResolverTest {

    private static final Map<String, List<JourneyDetails>> journeyDetailsMap = new HashMap<>();
    private static final String DIRECTORY = "C:\\Users\\JulianHoefler\\Projekte\\eigeneProjekte\\proof-of-concept-services\\src\\main\\resources\\data\\journeydetails";

    @Inject
    MapResolver mapResolver;

    @BeforeAll
    public static void setup() {
        journeyDetailsMap.put(DIRECTORY, List.of(new JourneyDetails()));

        FileNameResolver fileNameResolverMock = Mockito.mock(FileNameResolver.class);
        Mockito.when(fileNameResolverMock.resolvePath("key", "subDirectory")).thenReturn(DIRECTORY);
        QuarkusMock.installMockForType(fileNameResolverMock, FileNameResolver.class);
    }

    @Test
    void resolve_shouldReturnCorrectList() {
        List<JourneyDetails> result = mapResolver.resolve(journeyDetailsMap, "key", "subDirectory");

        Assertions.assertTrue(journeyDetailsMap.containsValue(result));
    }
}