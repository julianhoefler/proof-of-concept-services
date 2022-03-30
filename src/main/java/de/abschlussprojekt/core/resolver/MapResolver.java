package de.abschlussprojekt.core.resolver;

import de.abschlussprojekt.dmadapter.FileNameResolver;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class MapResolver {

    @Inject
    FileNameResolver fileNameResolver;

    public <T> List<T> resolve(Map<String, List<T>> map, String key, String subDirectory) {
        String path = fileNameResolver.resolvePath(key, subDirectory);
        return map.get(path);
    }
}
