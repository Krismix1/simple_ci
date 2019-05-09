package monitor;

import models.Commit;

import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("Repository path not specified");
            System.exit(1);
        }
        String repo = args[0];
        try {
            LocalFileSystemPollingMonitor monitor = new LocalFileSystemPollingMonitor(null, repo);
            final Optional<Commit> commit = monitor.checkNewCommits();
            commit.ifPresent(v -> System.out.println(v.getHash()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
