package monitor;

import messaging.Publisher;
import models.Commit;
import models.Repository;

import java.util.Optional;

public class LocalFileSystemPollingMonitor extends PollingMonitor {

    private static final String DEFAULT_BRANCH = "master";

    public LocalFileSystemPollingMonitor(Publisher publisher, String path) {
        super(publisher, new LocalFileSystemRepository(path));
    }

    @Override
    Optional<Commit> checkNewCommits() {
        return Math.random() < 0.5 ? Optional.empty() : Optional.of(new Commit(this.repository, DEFAULT_BRANCH, String.valueOf(Math.random())));
    }

    private static final class LocalFileSystemRepository extends Repository {
        private final String path;

        private LocalFileSystemRepository(String path) {
            this.path = path;
        }
    }

}
