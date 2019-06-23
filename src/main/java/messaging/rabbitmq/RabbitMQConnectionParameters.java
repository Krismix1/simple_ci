package messaging.rabbitmq;

public final class RabbitMQConnectionParameters {
    private String host;
    private int port;
    private String user;
    private String password;
    private String vhost;

    //@formatter:off
    private RabbitMQConnectionParameters() {}
    //@formatter:on

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getVhost() {
        return vhost;
    }

    public static class RabbitMQParametersBuilder {

        private RabbitMQConnectionParameters parameters;

        public RabbitMQParametersBuilder() {
            parameters = new RabbitMQConnectionParameters();
            // initialize with the default values of rabbitmq
            parameters.host = "localhost";
            parameters.port = 5672;
            parameters.user = "guest";
            parameters.password = "guest";
            parameters.vhost = "/";
        }

        public RabbitMQParametersBuilder host(String host) {
            this.parameters.host = host;
            return this;
        }

        public RabbitMQParametersBuilder onPort(int port) {
            this.parameters.port = port;
            return this;
        }

        public RabbitMQParametersBuilder withUser(String user) {
            this.parameters.user = user;
            return this;
        }

        public RabbitMQParametersBuilder withPassword(String password) {
            this.parameters.password = password;
            return this;
        }

        public RabbitMQParametersBuilder vhost(String vhost) {
            this.parameters.vhost = vhost;
            return this;
        }

        public RabbitMQConnectionParameters build() {
            RabbitMQConnectionParameters result = new RabbitMQConnectionParameters();
            result.host     = parameters.host
            result.port     = parameters.port
            result.user     = parameters.user
            result.password = parameters.password
            result.vhost    = parameters.vhost
            return result;
        }
    }
}
