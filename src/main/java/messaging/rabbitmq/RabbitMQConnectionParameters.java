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

        private String host = "localhost";
        private int port = 5672;
        private String user = "guest";
        private String password = "guest";
        private String vhost = "/";

        public RabbitMQParametersBuilder host(String host) {
            this.host = host;
            return this;
        }

        public RabbitMQParametersBuilder atPort(int port) {
            this.port = port;
            return this;
        }

        public RabbitMQParametersBuilder withUserCredentials(String user, String password) {
            this.user = user;
            this.password = password;
            return this;
        }

        public RabbitMQParametersBuilder vhost(String vhost) {
            this.vhost = vhost;
            return this;
        }


        public RabbitMQConnectionParameters build() {
            RabbitMQConnectionParameters parameters = new RabbitMQConnectionParameters();
            parameters.host     = this.host;
            parameters.port     = this.port;
            parameters.user     = this.user;
            parameters.vhost    = this.vhost;
            parameters.password = this.password;
            return parameters;
        }
    }
}
