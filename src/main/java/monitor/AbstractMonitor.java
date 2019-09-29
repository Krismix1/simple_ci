package monitor;

import messaging.Publisher;
import models.Commit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

abstract class AbstractMonitor {

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final Publisher<Commit> publisher;

    AbstractMonitor(Publisher<Commit> publisher) {
        this.publisher = Objects.requireNonNull(publisher);
    }

    /**
     * Method to be called when new commits are detected.
     * The commit will be added to a queue in order to be processed.
     *
     * @param commit the new commit, must not be null.
     * @throws IllegalArgumentException when the {@code commit} param is null.
     */
    void notifyRepoUpdated(Commit commit) {
        if (commit == null) {
            throw new IllegalArgumentException("Commit object should not be null");
        }
        logger.debug("New commit detected. Commit hash {}", commit.getHash());

        this.publisher.publishMessage(commit);
    }
}
