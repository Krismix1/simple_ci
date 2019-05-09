package monitor;

import messaging.Publisher;
import models.Commit;
import models.Repository;

import java.util.Optional;

// this class might become abstract
// as we can poll a repo on the local file system
// or over network (Github, GitLab, Bitbucket etc.)
public abstract class PollingMonitor extends AbstractMonitor implements Runnable {

    private boolean stopped = false;
    final Repository repository;

    PollingMonitor(Publisher publisher, Repository repository) {
        super(publisher);
        this.repository = repository;
    }

    @Override
    public void run() {
        while (!this.stopped) {
            this.checkNewCommits()
                    .ifPresent(this::notifyRepoUpdated);
            try {
                Thread.sleep(5000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    abstract Optional<Commit> checkNewCommits();

    public void stop() {
        this.stopped = true;
    }
}
