package de.abschlussprojekt.dmadapter.controller;

import de.abschlussprojekt.dmadapter.FileNameResolver;
import de.abschlussprojekt.dmadapter.repositories.DmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileLoader {

    @Autowired
    List<DmRepository> repositoryList;

    @Autowired
    FileNameResolver fileNameResolver;

    public void loadData() {
        repositoryList.forEach(
                dmRepository -> {
                    String subDirectory = dmRepository.getSubDirectory();
                    List<String> filePathList = fileNameResolver.resolveAllFilePathsInSubDirectory(subDirectory);
                    dmRepository.initializeData(filePathList);
                }
        );
    }
}
