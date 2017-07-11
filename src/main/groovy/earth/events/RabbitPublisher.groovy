package earth.events

import javax.inject.Inject
import com.rabbitmq.client.Channel
import com.rabbitmq.client.Connection
import com.rabbitmq.client.ConnectionFactory
import groovy.json.JsonOutput
import earth.Config

/**
 * Publisher implementation based on RabbitMQ
 *
 * @since 0.1.0
 */
class RabbitPublisher implements Publisher {

  @Inject
  ConnectionFactory factory

  @Inject
  Config config

  @Override
  void publish(String routingKey, Serializable event) {
    Connection connection = factory.newConnection()
    Channel channel = connection.createChannel()
    try {
      byte[] messageBodyBytes = JsonOutput.toJson(event).bytes
      channel.basicPublish(config.events.exchange, routingKey, null, messageBodyBytes)
    } finally {
      channel.close()
      connection.close()
    }
  }
}
