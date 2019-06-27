package filesystem;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

public class UnixFileSystemHelper implements FileSystemHelper {

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    @Override
    public String getDirectoryName(String path) throws IOException {
        // TODO: Check if the directory exists?
        path = this.getDirectoryPath(path);
        String[] paths = path.split(File.separator);
        return paths[paths.length - 1];
    }

    @Override
    public String join(String... paths) {
        if (paths == null || paths.length == 0) {
            throw new IllegalArgumentException("Can't join no paths");
        }

        final String separator = File.separator;
        final StringBuilder builder = new StringBuilder();

        for (String path : paths) {
            builder.append(path);
            if (!path.endsWith(separator)) {
                builder.append(separator);
            }
        }

        return builder.toString();
    }

    @Override
    public String getDirectoryPath(String path) throws IOException {
        // TODO: Check if the directory exists?
        Path pathObj = Paths.get(path)
                            .normalize()
                            .toRealPath()
                            .toAbsolutePath();

        if (Files.isRegularFile(pathObj)) {
            this.logger.warning(String.format(
                    "Path %s points to file. Extracting containing directory",
                    pathObj.toString()
            ));
            pathObj = pathObj.getParent();
        }
        return pathObj.toString();
    }

    @Override
    public boolean isDirectory(String path) {
        try {
            Path dirPath = Paths.get(this.getDirectoryPath(path));
            return Files.isDirectory(dirPath);
        } catch (IOException ex) {
            logger.info(String.format("Path not found: %s", ex.getMessage()));
            return false;
        }
    }
}
