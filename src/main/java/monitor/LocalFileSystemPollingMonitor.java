package monitor;

import messaging.Publisher;
import models.Commit;
import models.Repository;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.logging.Logger;

public class LocalFileSystemPollingMonitor extends PollingMonitor {

    private static final Logger logger = Logger.getLogger(LocalFileSystemPollingMonitor.class.getName());
    private static final String REPOSITORY_CLONE_DIRECTORY = "/tmp"; // TODO: Read this value from system info

    private static final String DEFAULT_BRANCH = "master";
    private static final int SUCCESS = 0;
    private final String sourcePath;
    private String clonePath;

    public LocalFileSystemPollingMonitor(Publisher publisher, String path) throws Exception {
        super(publisher, new LocalFileSystemRepository(path));
        this.sourcePath = path;
        this.init();
    }

    private void init() throws Exception {
        final String[] split = this.sourcePath.split(File.separator);
        String repoName = split[split.length - 1];
        this.clonePath = Paths.get(REPOSITORY_CLONE_DIRECTORY, repoName).toAbsolutePath().toString();

        Path scriptsFolder = this.getScriptsFolder();
        Process process = new ProcessBuilder("bash", "clone_repo_locally.sh", this.sourcePath, this.clonePath)
                .directory(scriptsFolder.toFile())
                .start();
        int exit_code = process.waitFor();
        if (exit_code != SUCCESS) {
            BufferedReader errorStream = new BufferedReader(new InputStreamReader(process.getErrorStream(), Charset.forName("UTF-8")));
            logger.severe(String.format("Could not clone repo. Reason: %s", errorStream.readLine()));
            throw new Exception("Failed to clone");
        }
    }

    private Path getScriptsFolder() {
        Path currentPath = Paths.get(System.getProperty("user.dir"));
        return Paths.get(currentPath.toString(), "src", "main", "java", "monitor");
    }

    @Override
    Optional<Commit> checkNewCommits() {
        // FIXME: 08-May-19 Extract file handling to a separate class?
        try {
            Path scriptsFolder = this.getScriptsFolder();
            Process process = new ProcessBuilder("bash", "check_commit.sh", this.clonePath)
                    .directory(scriptsFolder.toFile())
                    .start();

            int exit_code = process.waitFor();
            if (exit_code == SUCCESS) {
                File hashFile = Paths.get(scriptsFolder.toString(), ".commit_hash").toFile();
                try (BufferedReader input = new BufferedReader(new FileReader(hashFile))) {
                    String hash = input.readLine();
                    return Optional.of(new Commit(this.repository, DEFAULT_BRANCH, hash));
                } catch (IOException e) {
                    logger.severe(e.toString());
                }
            } else {
                // FIXME: 08-May-19 When there are no new commits, the check_commit.sh exits with 1, thus it enters this block
                BufferedReader errorStream = new BufferedReader(new InputStreamReader(process.getErrorStream(), Charset.forName("UTF-8")));
                logger.severe(String.format("Could not check for new commit. Reason: %s", errorStream.readLine()));
            }
        } catch (IOException | InterruptedException e) {
            logger.severe(e.toString());
        }
        return Optional.empty();
    }

    private static final class LocalFileSystemRepository extends Repository {
        private final String path;

        private LocalFileSystemRepository(String path) {
            this.path = path;
        }
    }

}
