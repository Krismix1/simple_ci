package models;

public class Commit {
    private Repository repository;
    private String branch; // to be converted to an object?
    private String hash;

    public Commit(Repository repository, String branch, String hash) {
        this.repository = repository;
        this.branch = branch;
        this.hash = hash;
    }

    public String getHash() {
        return hash;
    }
}
