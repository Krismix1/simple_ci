package monitor;

import messaging.Publisher;
import models.Commit;

abstract class AbstractMonitor {

    private final Publisher publisher;

    AbstractMonitor(Publisher publisher) {
        this.publisher = publisher;
    }

    void notifyRepoUpdated(Commit commit) {
        this.publisher.publishMessage(commit);
    }
}
