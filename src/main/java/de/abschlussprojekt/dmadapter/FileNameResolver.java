package de.abschlussprojekt.dmadapter;

import javax.enterprise.context.ApplicationScoped;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@ApplicationScoped
public class FileNameResolver {

    private static final String BASE_DIRECTORY = "C:\\Users\\JulianHoefler\\Projekte\\eigeneProjekte\\proof-of-concept-services\\src\\main\\resources\\data\\";

    public String resolvePath(String fileName, String subDirectory) {
        String uri = BASE_DIRECTORY + subDirectory + "\\" + withSuffix(fileName);

        if (pathExists(uri)) {
            return uri;
        }

        return "No corresponding JSON file.";
    }

    public List<String> resolveAllFilePathsInSubDirectory(String subDirectory) {
        String uri = BASE_DIRECTORY + subDirectory;
        List<String> filePathList = new ArrayList<>();

        try (Stream<Path> paths = Files.walk(Paths.get(uri))) {
            paths.filter(Files::isRegularFile)
                    .forEach(filePath -> filePathList.add(filePath.toString()));

        } catch (IOException e) {
            e.printStackTrace();
        }

        return filePathList;
    }

    public boolean fileExistsInJourneyDetails(String detailsId) {
        String uri = BASE_DIRECTORY + "journeydetails" + "\\" + withSuffix(getMd5Hash(detailsId));

        return pathExists(uri);
    }

    public String getMd5Hash(String string) {
        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");
            instance.reset();
            instance.update(string.getBytes());
            byte[] digest = instance.digest();
            BigInteger bigInteger = new BigInteger(1, digest);
            StringBuilder hashtext = new StringBuilder(bigInteger.toString(16));

            while (hashtext.length() < 32) {
                hashtext.insert(0, "0");
            }

            return hashtext.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return string;
    }

    private boolean pathExists(String path) {
        try {
            return new File(path).exists();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private String withSuffix(String fileName) {
        return fileName + ".json";
    }
}
