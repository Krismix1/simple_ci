package monitor;

import messaging.Publisher;
import models.Commit;
import models.Repository;

import java.util.Arrays;
import java.util.Optional;

// this class is abstract
// as we can poll a repo on the local file system
// or over network (Github, GitLab, Bitbucket etc.)
public abstract class PollingMonitor extends AbstractMonitor {

    private boolean stopped = false;
    final Repository repository;

    PollingMonitor(Publisher<Commit> publisher, Repository repository) {
        super(publisher);
        this.repository = repository;
    }

    public final void run() {
        while (!this.stopped) {
            // The lines bellow can be written as this:
//            Optional<Commit> commit = this.checkNewCommits();
//            if (commit.isPresent()) {
//                this.notifyRepoUpdated(commit.get()); // template pattern
//            }
            this.checkNewCommits()
                    .ifPresent(this::notifyRepoUpdated); // template pattern

            try {
                Thread.sleep(5000L);
            } catch (InterruptedException e) {
                logger.fine(String.format("Caught exception while waiting: %s", Arrays.toString(e.getStackTrace())));
            }
        }
        logger.finest("PollingMonitor has stopped");
    }

    abstract Optional<Commit> checkNewCommits();

    public void stop() {
        logger.finest("stop method called");
        this.stopped = true;
    }
}
