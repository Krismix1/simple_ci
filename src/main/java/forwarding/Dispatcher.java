package forwarding;

import forwarding.handlers.RequestHandler;
import messaging.Consumer;

import java.util.LinkedList;
import java.util.List;

// Structuring a system into subsystems helps reduce complexity. A common design
// goal is to minimize the communication and dependencies between subsystems. One
// way to achieve this goal is to introduce a facade object that provides a single,
// simplified interface to the more general facilities of a subsystem.
class Dispatcher {

    private final List<RequestHandler> handlers;
    private final Consumer consumer;

    Dispatcher(Consumer consumer) {
        this.consumer = consumer;
        this.handlers = new LinkedList<>();
//        handlers.add(handlers.size(), RequestHandler.DEFAULT_COMMAND_HANDLER);
    }

    public void onMessageHandle(Object message) {
//         handlers.get(0).handle((Request) message);
    }
}
