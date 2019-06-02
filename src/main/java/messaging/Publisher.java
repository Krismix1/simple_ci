package messaging;

public interface Publisher<T extends Message> {
    void publishMessage(T message);
}
