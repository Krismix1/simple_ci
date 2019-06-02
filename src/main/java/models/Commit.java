package models;

import messaging.Message;

public class Commit implements Message {
    private Repository repository;
    private String branch; // TODO: 6/2/19 to be converted to an object?
    private String hash;

    public Commit(Repository repository, String branch, String hash) {
        this.repository = repository;
        this.branch = branch;
        this.hash = hash;
    }

    public String getHash() {
        return hash;
    }

    @Override
    public byte[] serialize() {
        String message = branch + "-" + hash;
        return message.getBytes();
    }
}
