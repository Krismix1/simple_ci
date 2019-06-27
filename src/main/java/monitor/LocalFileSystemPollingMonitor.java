package monitor;

import exceptions.OperationFailedException;
import filesystem.FileSystemHelper;
import filesystem.UnixFileSystemHelper;
import messaging.Publisher;
import models.Commit;
import models.Repository;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.MergeCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;

import java.io.File;
import java.util.List;
import java.util.Optional;


public class LocalFileSystemPollingMonitor extends PollingMonitor {

    private static final String REPOSITORY_CLONE_DIRECTORY = System.getProperty("java.io.tmpdir");

    private static final String DEFAULT_BRANCH = "master";
    private static final int SUCCESS = 0;

    private final String sourcePath;
    private String clonePath;
    private Git sourceRepoObj;
    private Git cloneRepoObj;
    private final FileSystemHelper fsHelper;

    public LocalFileSystemPollingMonitor(Publisher<Commit> publisher, String path) throws Exception {
        super(publisher, new LocalFileSystemRepository(path));
        fsHelper = new UnixFileSystemHelper();
        sourcePath = fsHelper.getDirectoryPath(path);
        init();
    }

    private void init() throws Exception {
        String repoName = fsHelper.getDirectoryName(sourcePath);
        clonePath = fsHelper.join(REPOSITORY_CLONE_DIRECTORY, repoName);

        logger.finest(String.format("Will clone %s to %s", sourcePath, clonePath));

        sourceRepoObj = Git.open(new File(sourcePath));

        // Check if clone directory exists
        if (fsHelper.isDirectory(clonePath)) {
            // TODO: If so, initialize the git object
            // Read the last commit
            // Create a new job for each new commit from that point
            cloneRepoObj = Git.open(new File(clonePath));
        } else {
            // else, clone the repo
            cloneRepoObj = Git.cloneRepository()
                .setURI(sourcePath)
                .setDirectory(new File(clonePath))
                .call();
        }
    }

    @Override
    Optional<Commit> checkNewCommits() {
        String sourceCommit = getCommitHash(sourceRepoObj, DEFAULT_BRANCH)
            .orElseThrow(() -> new OperationFailedException(
                String.format("Failed to retrieve last commit for repo %s, branch %s",
                sourceRepoObj.getRepository().getIdentifier(),
                DEFAULT_BRANCH))
            );
        String cloneCommit = getCommitHash(cloneRepoObj, DEFAULT_BRANCH)
            .orElseThrow(() -> new OperationFailedException(
                String.format("Failed to retrieve last commit for repo %s, branch %s",
                    cloneRepoObj.getRepository().getIdentifier(),
                    DEFAULT_BRANCH))
            );
        if (sourceCommit.equals(cloneCommit)) {
            return Optional.empty();
        }
        // TODO: 6/27/19 Generate intermediate entries in the queue?
        try {
            cloneRepoObj
                .pull()
                .setFastForward(MergeCommand.FastForwardMode.FF_ONLY)
                .call();
        } catch (GitAPIException e) {
            logger.severe(String.format(
                "Failed to fast-forward repo %s, branch %s",
                cloneRepoObj.getRepository().getIdentifier(),
                DEFAULT_BRANCH
            ));
            // let's try again the next time
            return Optional.empty();
        }
        return Optional.of(new Commit(repository, DEFAULT_BRANCH, sourceCommit));
    }

    private Optional<String> getCommitHash(Git repo, String branchId) {
        RevWalk walk = new RevWalk(repo.getRepository());
        try {
            List<Ref> branches = repo.branchList().call();
            for (Ref branch : branches) {
                if (branch.getName().endsWith(branchId)) {
                    RevCommit commit = walk.lookupCommit(branch.getObjectId());
                    return Optional.of(commit.getName());
                }
            }
        } catch (GitAPIException e) {
            logger.severe(String.format("Failed to retrieve last commit for repo %s, branch %s",
                repo.getRepository().getIdentifier(),
                branchId));
        }
        return Optional.empty();
    }

    private static final class LocalFileSystemRepository extends Repository {
        private final String path;

        private LocalFileSystemRepository(String path) {
            this.path = path;
        }
    }

}
