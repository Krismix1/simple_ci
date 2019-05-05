package forwarding.handlers;

import java.util.Objects;

public abstract class RequestHandler {
    public static final RequestHandler DEFAULT_COMMAND_HANDLER = new RequestHandler() {
        @Override
        public boolean handle(Request req) {
            System.err.println(String.format("No handler for command '%s' with arguments '%s'", req.getCommand(), req.getArgs()));
            return false;
        }
    };

    RequestHandler successor;

    /**
     * Handling of commands coming from TCP clients.
     *
     * @param request contains information about the request to be handled
     * @return {@code true} if handled, {@code false} otherwise
     */
    public abstract boolean handle(Request request);

    public void setSuccessor(RequestHandler successor) {
        this.successor = Objects.requireNonNull(successor);
    }
}
