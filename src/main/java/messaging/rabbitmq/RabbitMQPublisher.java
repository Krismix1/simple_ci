package messaging.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;
import messaging.Message;
import messaging.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Objects;

public class RabbitMQPublisher<T extends Message> implements Publisher<T> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private Connection connection;
    private Channel channel;
    private String exchangeName;
    private String routingKey;
    private String queueName;

    private boolean initialized = false; // TODO: This needs second thoughts

    public RabbitMQPublisher(RabbitMQConnectionFactory connectionFactory) {
        this.connection = connectionFactory.getConnection();
    }

    public void init(String queueName) {
        this.queueName = Objects.requireNonNull(queueName);
        this.exchangeName = this.queueName + ".exchange";
        this.routingKey = this.queueName + ".routing";

        try {
            this.channel = this.connection.createChannel();
            channel.exchangeDeclare(exchangeName, "direct", true);
            channel.queueDeclare(queueName, true, false, false, null).getQueue();
            channel.queueBind(queueName, exchangeName, routingKey);
        } catch (IOException e) {
            logger.error("Caught exception while initializing queue {}", queueName);
        }
    }

    @Override
    public void publishMessage(T message) {

        if (!initialized) {
            init("commits"); // FIXME: Need to find a better way to manage the RabbitMQ objects
            initialized = true;
        }

        if (this.channel == null) {
            throw new IllegalStateException("The connection is not active.");
        }

        try {
            channel.basicPublish(
                    exchangeName,
                    routingKey,
                    MessageProperties.PERSISTENT_BASIC,
                    message.serialize()
            );
        } catch (IOException e) {
            logger.error("Caught exception while publishing message '{}' to queue {}", message, queueName);
        }
    }
}
