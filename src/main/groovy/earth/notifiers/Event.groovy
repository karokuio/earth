package earth.notifiers

import groovy.transform.TupleConstructor

/**
 * Represents Karoku events
 *
 * @since 0.1.0
 */
@TupleConstructor
class Event {

  /**
   * Type of event
   *
   * @since 0.1.0
   */
  String type

  /**
   * Object that can be serialized to JSON
   *
   * @since 0.1.0
   */
  Serializable payload
}
