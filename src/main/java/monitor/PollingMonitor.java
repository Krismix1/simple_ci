package monitor;

import messaging.Publisher;
import models.Commit;
import models.Repository;
import sleepers.Sleeper;

import java.util.Arrays;
import java.util.Optional;

// this class is abstract
// as we can poll a repo on the local file system
// or over network (Github, GitLab, Bitbucket etc.)
abstract class PollingMonitor extends AbstractMonitor {

    private boolean running = false;
    final Repository repository;
    private final Sleeper sleeper;

    PollingMonitor(Publisher<Commit> publisher, Repository repository, Sleeper sleeper) {
        super(publisher);
        this.repository = repository;
        this.sleeper = sleeper;
    }

    final void run() {
        this.init();

        running = true;
        while (running) {

            doCheck();

            try {
                sleeper.sleep(5000);
            } catch (InterruptedException e) {
                logger.debug("Caught exception while waiting: {}", e);
            }
        }
        logger.debug("PollingMonitor has stopped");
    }

    abstract Optional<Commit> checkNewCommits();

    abstract void init();

    void stop() {
        running = false;
        logger.debug("stop method called");
    }

    void doCheck() {
        this.checkNewCommits()
            .ifPresent(this::notifyRepoUpdated);
    }
}
