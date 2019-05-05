package forwarding.handlers;

public class StatusHandler extends RequestHandler {
    @Override
    public boolean handle(Request req) {
        if (req.getCommand().equals("status")) {
            System.out.println("Status handler called with ");
            return true;
        }
        return this.successor.handle(req);
    }
}
