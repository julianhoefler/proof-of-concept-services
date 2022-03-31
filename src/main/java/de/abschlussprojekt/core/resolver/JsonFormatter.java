package de.abschlussprojekt.core.resolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

@Service
public class JsonFormatter {

    @Autowired
    ObjectMapper objectMapper;

    public <T> List<T> getFileAsList(String absoluteFilePath, Class<T> itemClass) {

        CollectionType collectionType = objectMapper.getTypeFactory().constructCollectionType(List.class, itemClass);

        try {
            return objectMapper.readValue(getFileAsString(absoluteFilePath), collectionType);
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    private String getFileAsString(String filePath) {
        try {
            return new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (Exception e) {
            e.printStackTrace();
            return filePath;
        }
    }
}
