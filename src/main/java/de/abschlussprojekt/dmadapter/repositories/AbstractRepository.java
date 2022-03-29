package de.abschlussprojekt.dmadapter.repositories;

import de.abschlussprojekt.core.resolver.JsonFormatter;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractRepository<T> implements DmRepository {

    private final Map<String, List<T>> data = new HashMap<>();
    private final Class<T> sourceContentType;

    @Inject
    JsonFormatter jsonFormatter;

    protected AbstractRepository(Class<T> sourceContentType) {
        this.sourceContentType = sourceContentType;
    }

    public List<T> getByName(String name){
        return data.get(name);
    }

    @Override
    public void initializeData(List<String> filePathList) {
        filePathList.forEach(filePath -> {
            data.put(filePath, jsonFormatter.getFileAsList(filePath, sourceContentType));
        });
    }

    @Override
    public abstract String getSubDirectory();
}
