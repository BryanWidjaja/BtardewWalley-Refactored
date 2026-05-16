package services.loader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TutorialPageLoader {
    private static final String TITLE_PREFIX = "### ";

    public static List<String[]> loadPages(String path) {
        List<String[]> pages = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            List<String> currentPage = null;
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith(TITLE_PREFIX)) {
                    if (currentPage != null) {
                        pages.add(currentPage.toArray(new String[0]));
                    }
                    currentPage = new ArrayList<>();
                    currentPage.add(line.substring(TITLE_PREFIX.length()));
                } else if (currentPage != null) {
                    currentPage.add(line);
                }
            }
            if (currentPage != null) {
                pages.add(currentPage.toArray(new String[0]));
            }
        } catch (IOException exception) {
            System.out.println("Tutorial file could not be read.");
        }
        return pages;
    }
}
