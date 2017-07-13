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

  static final String QUEUE_TEMPLATES = 'templates'
  static final String QUEUE_DOCKER = 'docker'
  static final String QUEUE_EVENTS = 'events'

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
    channel.queueDeclare(QUEUE_TEMPLATES, false, false, false, null)
    channel.queueDeclare(QUEUE_DOCKER, false, false, false, null)
    channel.queueDeclare(QUEUE_EVENTS, false, false, false, null)

    channel.queueBind(QUEUE_TEMPLATES, config.events.publish.exchange, "*.templates.*")
    channel.queueBind(QUEUE_DOCKER, config.events.publish.exchange, "*.docker.*")
    channel.queueBind(QUEUE_EVENTS, config.events.publish.exchange, "event.#")

    channel.close()
    connection.close()

    return factory
  }
}
