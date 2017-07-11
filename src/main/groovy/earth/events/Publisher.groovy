package earth.events

/**
 * Notifies of events happening in the system
 *
 * @since 0.1.0
 */
interface Publisher {

  /**
   * Notifies that a given event has occured
   *
   * @param type event type
   * @param payload the event's payload
   * @since 0.1.0
   */
  void publish(String type, Serializable payload)
}
