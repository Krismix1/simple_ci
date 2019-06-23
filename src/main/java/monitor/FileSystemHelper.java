package monitor;

import java.io.IOException;

public interface FileSystemHelper {
    /**
    * @param path - the path to the directory, can be relative or absolute.
    * @return - the name of the directory to where the path points.
    */
    String getDirectoryName(String path) throws IOException;

    String join(String... paths);

    String getDirectoryPath(String path) throws IOException;
}
