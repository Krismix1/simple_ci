package filesystem;

import java.io.IOException;

public interface FileSystemHelper {
    /**
    * @param path - the path to the directory, can be relative or absolute.
    * @return - the name of the directory to where the path points.
    */
    String getDirectoryName(String path) throws IOException;

    /**
     * Joins the paths together.
     * @param paths an array of paths, must not be {@code null}.
     * @return a string representing the path, based on the operating system.
     */
    String join(String... paths);

    String getDirectoryPath(String path) throws IOException;

    /**
     * Checks if the path points to a directory.
     * @param path must not be null.
     * @return true if it does, false otherwise.
     */
    boolean isDirectory(String path);
}
