package earth.notifiers

import javax.inject.Provider
import javax.inject.Inject
import com.rabbitmq.client.Channel
import com.rabbitmq.client.Connection
import com.rabbitmq.client.ConnectionFactory
import earth.Config

/**
 * Initializes the RabbitMQ connection
 *
 * @since 0.1.0
 */
class ConnectionFactoryProvider implements Provider<ConnectionFactory> {

  @Inject
  Config configuration

  @Override
  ConnectionFactory get() {
    ConnectionFactory factory = new ConnectionFactory()

    factory.setUsername(configuration.events.username)
    factory.setPassword(configuration.events.password)
    factory.setHost(configuration.events.host)
    factory.setPort(configuration.events.port)

    Connection connection = factory.newConnection()
    Channel channel = connection.createChannel()

    Config.Events rabbitConfiguration = configuration.events
    List<String> queues = rabbitConfiguration.pipes

    channel.exchangeDeclare(rabbitConfiguration.exchange, "topic")
    channel.queueDeclare("templates", false, false, false, null)
    channel.queueDeclare("docker", false, false, false, null)
    channel.queueBind("templates", rabbitConfiguration.exchange, "templates.*")
    channel.queueBind("docker", rabbitConfiguration.exchange, "docker.*")

    channel.close()
    connection.close()

    return factory
  }
}
