package general;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

public abstract class AReader implements Reader{

    protected static ArrayList<File> initFiles(String problem) {
        ArrayList<File> files = new ArrayList<>();
        try (Stream<Path> paths = Files.walk(Paths.get("resources/" + problem))) {
            paths.filter(Files::isRegularFile)
                    .forEach(path -> files.add(new File(path.toString())));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return files;
    }

}
