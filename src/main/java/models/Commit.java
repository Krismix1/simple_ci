package models;

public class Commit {
    public String repository; // to be converted to an object?
    public String branch; // to be converted to an object?
    public String hash;

    public Commit(String repository, String branch, String hash) {
        this.repository = repository;
        this.branch = branch;
        this.hash = hash;
    }
}
