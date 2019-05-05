package forwarding.handlers;

public class Request {
    /**
     * The command to be handled, e.g. 'status', 'dispatch' etc.
     */
    private final String command;
    /**
     * Arguments for the command.
     */
    private final String args;

    public Request(String command, String args) {
        this.command = command;
        this.args = args;
    }

    public String getCommand() {
        return command;
    }

    public String getArgs() {
        return args;
    }
}
