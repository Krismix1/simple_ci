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

    void notifyRepoUpdated(Commit commit) {
        System.out.println("New commit detected");
        System.out.println("Commit hash " + commit.getHash());
        this.publisher.publishMessage(commit);
    }
}
