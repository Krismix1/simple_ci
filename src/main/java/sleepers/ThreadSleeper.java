package sleepers;

public class ThreadSleeper implements Sleeper {
    @Override
    public void sleep(long millis) throws InterruptedException {
        // FIXME: 8/29/19 Use wait() instead of Thread.sleep()?
        Thread.sleep(millis);
    }
}
