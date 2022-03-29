package de.abschlussprojekt.dmadapter.repositories;

import java.util.List;

public interface DmRepository {

    void initializeData(List<String> filePathList);

    String getSubDirectory();
}
