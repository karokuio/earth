package earth.notifiers

import javax.inject.Inject
import com.rabbitmq.client.Channel
import com.rabbitmq.client.Connection
import com.rabbitmq.client.ConnectionFactory
import groovy.json.JsonOutput

/**
 * Notifier implementation based on RabbitMQ
 *
 * @since 0.1.0
 */
class RabbitNotifier implements Notifier {

  @Inject
  ConnectionFactory factory

  @Override
  void event(String routingKey, Event event) {
    Connection connection = factory.newConnection()
    Channel channel = connection.createChannel()
    try {
      byte[] messageBodyBytes = JsonOutput.toJson(event).bytes
      channel.basicPublish("", routingKey, null, messageBodyBytes)
    } finally {
      channel.close()
      connection.close()
    }
  }
}
