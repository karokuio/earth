package earth.notifiers

import com.google.inject.Scopes
import com.google.inject.AbstractModule
import com.rabbitmq.client.ConnectionFactory

/**
 * Loads RabbitMQ related services
 *
 * @since 0.1.0
 */
class Module extends AbstractModule {
  @Override
  void configure() {
    bind(ConnectionFactory)
    .toProvider(ConnectionFactoryProvider)
    .in(Scopes.SINGLETON)

    bind(Notifier)
    .to(RabbitNotifier)
    .in(Scopes.SINGLETON)
  }
}