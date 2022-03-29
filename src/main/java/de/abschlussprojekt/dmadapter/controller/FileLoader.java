package de.abschlussprojekt.dmadapter.controller;

import de.abschlussprojekt.dmadapter.FileNameResolver;
import de.abschlussprojekt.dmadapter.repositories.DmRepository;
import io.quarkus.arc.All;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class FileLoader {

    @Inject
    @All
    List<DmRepository> repositoryList;

    @Inject
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
