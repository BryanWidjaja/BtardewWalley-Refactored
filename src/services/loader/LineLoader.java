package services.loader;

import java.io.IOException;

public interface LineLoader {
    void load(String[] parts);

    default void loadFile(String path) throws IOException {
        for (String[] row : FileLineReader.readRows(path)) {
            load(row);
        }
    }
}
