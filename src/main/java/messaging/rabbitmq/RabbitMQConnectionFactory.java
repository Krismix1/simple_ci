package messaging.rabbitmq;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitMQConnectionFactory {

    private final ConnectionFactory connectionFactory;

    public RabbitMQConnectionFactory(RabbitMQConnectionParameters parameters) {
        this.connectionFactory = new ConnectionFactory();

        connectionFactory.setUsername(parameters.getUser());
        connectionFactory.setPassword(parameters.getPassword());
        connectionFactory.setVirtualHost(parameters.getVhost());
        connectionFactory.setHost(parameters.getHost());
        connectionFactory.setPort(parameters.getPort());
    }

    Connection getConnection() throws IOException, TimeoutException {
        return connectionFactory.newConnection();
    }
}
