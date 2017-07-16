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
    channel.queueDeclare(Queues.TEMPLATES, false, false, false, null)
    channel.queueDeclare(Queues.DOCKER, false, false, false, null)
    channel.queueDeclare(Queues.EVENTS, false, false, false, null)

    channel.queueBind(Queues.TEMPLATES, config.events.publish.exchange, Events.TEMPLATE_ALL)
    channel.queueBind(Queues.DOCKER, config.events.publish.exchange, Events.DOCKER_ALL)
    channel.queueBind(Queues.EVENTS, config.events.publish.exchange, Events.ALL)

    channel.close()
    connection.close()

    return factory
  }
}
