package monitor;

import messaging.Publisher;
import messaging.rabbitmq.RabbitMQConnectionFactory;
import messaging.rabbitmq.RabbitMQConnectionParameters;
import messaging.rabbitmq.RabbitMQPublisher;
import models.Commit;
import sleepers.ThreadSleeper;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("Repository path not specified");
            System.exit(1);
        }
        String repo = args[0];
        Logger rootLogger = LogManager.getLogManager().getLogger("");
        rootLogger.setLevel(Level.ALL);
        for (Handler h : rootLogger.getHandlers()) {
            h.setLevel(Level.ALL);
        }

        try {

            Publisher<Commit> publisher = new RabbitMQPublisher<>(new RabbitMQConnectionFactory(
                    new RabbitMQConnectionParameters.RabbitMQParametersBuilder()
                            .build()
            ));

            LocalFileSystemPollingMonitor monitor = new LocalFileSystemPollingMonitor(
                publisher, repo, new ThreadSleeper()
            );
            // add sigterm handling
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                System.out.println("\r\nReceived termination signal.\r\nCleaning up the mess...");
                monitor.stop();
            }));

            monitor.run();
            System.out.println("Monitor daemon stopped");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
