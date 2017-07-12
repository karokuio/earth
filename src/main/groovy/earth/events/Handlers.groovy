package earth.events

import static ratpack.sse.ServerSentEvents.serverSentEvents

import ratpack.handling.Context
import ratpack.sse.Event
import ratpack.sse.ServerSentEvents

import reactor.core.publisher.Flux
import reactor.core.publisher.FluxSink
import org.reactivestreams.Publisher
import com.rabbitmq.client.Channel
import com.rabbitmq.client.Envelope
import com.rabbitmq.client.AMQP
import com.rabbitmq.client.Connection
import com.rabbitmq.client.ConnectionFactory
import com.rabbitmq.client.DefaultConsumer

/**
 * Handlers used to publish Karoku events
 *
 * @since 0.1.0
 */
class Handlers {

  /**
   * Sends through SSE all events produced by Karoku
   *
   * @since 0.1.0
   */
  static void all(final Context ctx) {
    ConnectionFactory factory = ctx.get(ConnectionFactory)
    Channel channel = factory.newConnection().createChannel()

    Flux<Event> management = createPublisherFor("templates", channel)
    Flux<Event> docker = createPublisherFor("docker", channel)
    Flux<Event> eventHose = Flux.merge(management, docker)

    ServerSentEvents allEvents = serverSentEvents(eventHose, Handlers.&toSSEvent)

    ctx.render(allEvents)
  }

  /**
   * This method transforms a notification coming from JDBC to a SSE
   * event
   *
   * @param e the raw event
   * @return the event with the required data
   * @since 0.1.0
   */
  static Event toSSEvent(final Event e) {
    return e.data { String raw -> raw }
  }

  /**
   * Creates a reactive publisher of type {@link Flush}
   *
   * @param queue the queue the publisher will be getting events from
   * @param channel the broker channel
   * @return an instance of {@link Flush} that can be used to publish
   * events in the Server Sent Events endpoint
   * @since 0.1.0
   */
  static Flux<Event> createPublisherFor(final String queue, final Channel channel) {
    return Flux.create { FluxSink sink ->
      channel.basicConsume(queue, new DefaultConsumer(channel) {
        @Override
        void handleDelivery(String consumerTag,
                      Envelope envelope,
                      AMQP.BasicProperties properties,
                      byte[] body) {
            sink.next(new String(body))
        }
      })

      sink.onDispose {
        if (!channel.isClosed()) {
          channel.close()
          channel.connection.close()
        }
      }
    }
  }
}
