package earth.events

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
  Config config

  @Override
  ConnectionFactory get() {
    ConnectionFactory factory = new ConnectionFactory()

    factory.setUsername(config.events.broker.username)
    factory.setPassword(config.events.broker.password)
    factory.setHost(config.events.broker.host)
    factory.setPort(config.events.broker.port)

    Connection connection = factory.newConnection()
    Channel channel = connection.createChannel()

    channel.exchangeDeclare(config.events.publish.exchange, "topic")
    channel.queueDeclare("templates", false, false, false, null)
    channel.queueDeclare("docker", false, false, false, null)
    channel.queueBind("templates", config.events.publish.exchange, "templates.*")
    channel.queueBind("docker", config.events.publish.exchange, "docker.*")

    channel.close()
    connection.close()

    return factory
  }
}
