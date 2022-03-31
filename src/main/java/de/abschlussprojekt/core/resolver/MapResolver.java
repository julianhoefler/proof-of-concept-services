package de.abschlussprojekt.core.resolver;

import de.abschlussprojekt.dmadapter.FileNameResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MapResolver {

    @Autowired
    FileNameResolver fileNameResolver;

    public <T> List<T> resolve(Map<String, List<T>> map, String key, String subDirectory) {
        String path = fileNameResolver.resolvePath(key, subDirectory);
        return map.get(path);
    }
}
