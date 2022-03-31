package de.abschlussprojekt.dmadapter.repositories;

import de.abschlussprojekt.core.resolver.JsonFormatter;
import de.abschlussprojekt.dmadapter.FileNameResolver;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractRepository<T> implements DmRepository {

    Map<String, List<T>> data = new HashMap<>();
    private final Class<T> sourceContentType;

    @Autowired
    JsonFormatter jsonFormatter;

    @Autowired
    FileNameResolver fileNameResolver;

    protected AbstractRepository(Class<T> sourceContentType) {
        this.sourceContentType = sourceContentType;
    }

    public List<T> getById(String name){
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
