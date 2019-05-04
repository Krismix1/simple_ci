package monitor;

import forwarding.Dispatcher;
import models.Commit;

import java.util.Optional;

// this class might become abstract
// as we can poll a repo on the local file system
// or over HTTP (Github, GitLab, Bitbucket etc.)
public class PollingMonitor extends AbstractMonitor implements Runnable {

    private static final String DEFAULT_BRANCH = "master";

    private boolean stopped = false;
    private final String repository;

    public PollingMonitor(Dispatcher forwarder, String repository) {
        super(forwarder);
        this.repository = repository;
    }

    @Override
    public void run() {
        while (!this.stopped) {
            checkNewCommits()
                    .ifPresent(this::notifyRepoUpdated);
            try {
                Thread.sleep(5000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private Optional<Commit> checkNewCommits() {
        return Math.random() < 0.5 ? Optional.empty() : Optional.of(new Commit(this.repository, DEFAULT_BRANCH, String.valueOf(Math.random())));
    }

    public void stop() {
        this.stopped = true;
    }

}
