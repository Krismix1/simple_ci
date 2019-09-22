package sleepers;

public interface Sleeper {

    /**
     * Wait/sleep for an amount of time.
     * @param millis amount of time in milliseconds
     * @throws InterruptedException if the wait/sleep operation was interrupted before finishing.
     */
    void sleep(long millis) throws InterruptedException;
}
