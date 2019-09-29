package monitor;

import messaging.Publisher;
import messaging.rabbitmq.RabbitMQConnectionFactory;
import messaging.rabbitmq.RabbitMQConnectionParameters;
import messaging.rabbitmq.RabbitMQPublisher;
import models.Commit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sleepers.ThreadSleeper;

import java.io.IOException;


public class Main {

    private final static Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.err.println("Repository path not specified");
            System.exit(1);
        }
        String repo = args[0];


        Publisher<Commit> publisher = new RabbitMQPublisher<>(new RabbitMQConnectionFactory(
            new RabbitMQConnectionParameters.RabbitMQParametersBuilder()
                .build()
        ));

        LocalFileSystemPollingMonitor monitor = new LocalFileSystemPollingMonitor(
            publisher, repo, new ThreadSleeper()
        );
        // add sigterm handling
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            LOGGER.info("\r\nReceived termination signal.\r\nCleaning up the mess...");
            monitor.stop();
        }));

        monitor.run();
        LOGGER.info("Monitor daemon stopped");
    }
}
