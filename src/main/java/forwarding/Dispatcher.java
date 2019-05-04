package forwarding;

import models.Commit;

public abstract class Dispatcher {
    public abstract boolean healthy();

    public abstract boolean forwardCommit(Commit commit);

    public abstract Object status();
}
