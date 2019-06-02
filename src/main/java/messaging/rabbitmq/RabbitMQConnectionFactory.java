package messaging.rabbitmq;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

// docker run -d --hostname rabbitmq --name rabbitmq -p 15672:15672 -p 5672:5672 rabbitmq:3.7.14-management
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

    public Connection getConnection() {
        try {
            return this.connectionFactory.newConnection();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
        return null;
    }
}
