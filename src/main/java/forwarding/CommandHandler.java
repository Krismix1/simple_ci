package forwarding;

public abstract class CommandHandler {
    /**
     * Handling of commands coming from TCP clients.
     * @param command the command to be handled, e.g. 'status', 'dispatch' etc
     * @param args arguments for the command
     * @return {@code true} if handled, {@code false} otherwise
     */
    public abstract boolean handle(String command, String args);
}
