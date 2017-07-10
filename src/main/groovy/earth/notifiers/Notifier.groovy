package earth.notifiers

/**
 * Notifies of events happening in the system
 *
 * @since 0.1.0
 */
interface Notifier {

  /**
   * Notifies that a given event has occured
   *
   * @param type event type
   * @param payload extra information about the event
   * @since 0.1.0
   */
  void event(String type, Serializable payload)
}
