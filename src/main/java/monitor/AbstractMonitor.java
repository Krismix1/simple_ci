package monitor;

import forwarding.Dispatcher;
import models.Commit;

public abstract class AbstractMonitor {

    public AbstractMonitor(Dispatcher forwarder) {
    }

    protected boolean notifyRepoUpdated(Commit commit) {
        throw new UnsupportedOperationException();
    }
}
