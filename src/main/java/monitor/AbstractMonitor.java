package monitor;

import messaging.Publisher;
import models.Commit;

import java.util.Objects;
import java.util.logging.Logger;

abstract class AbstractMonitor {

    // TODO: 6/2/19 Use the sl4j logger
    final Logger logger = Logger.getLogger(this.getClass().getName());

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
        this.logger.finest("New commit detected");
        this.logger.finest("Commit hash " + commit.getHash());

        this.publisher.publishMessage(commit);
    }
}
