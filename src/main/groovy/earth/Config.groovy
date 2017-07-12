package earth

/**
 * Earth configuration specification
 *
 * @since 0.1.0
 */
class Config {

  /**
   * Data storage configuration
   *
   * @since 0.1.0
   */
  Data data

  /**
   * Event notifications configuration
   *
   * @since 0.1.0
   */
  Events events

  /**
   * Information related to disk storage locations
   *
   * @since 0.1.0
   */
  Storage storage

  /**
   * Underlying data store configuration
   *
   * @since 0.1.0
   */
  static class Data {

    /**
     * Host of the data store
     *
     * @since 0.1.2
     */
    String contactPoint

    /**
     * Data store username
     *
     * @since 0.1.0
     */
    String username

    /**
     * Data store password
     *
     * @since 0.1.0
     */
    String password
  }

  /**
   * Event queuing configuration
   *
   * @since 0.1.0
   */
  static class Events {

    /**
     * Information related to the message broker
     *
     * @since 0.1.0
     */
    Broker broker

    /**
     * Message publishing locations
     *
     * @since 0.1.0
     */
    Publish publish
  }

  /**
   * Information related to the message broker
   *
   * @since 0.1.0
   */
  static class Broker {

    /**
     * Username of the message broker
     *
     * @since 0.1.0
     */
    String username

    /**
     * Password of the message broker
     *
     * @since 0.1.0
     */
    String password

    /**
     * Host of the message broker
     *
     * @since 0.1.0
     */
    String host

    /**
     * Port of the message broker
     *
     * @since 0.1.0
     */
    Integer port
  }

  /**
   * Information about the exchange messages are going to be published
   *
   * @since 0.1.0
   */
  static class Publish {

    /**
     * Exchange name
     *
     * @since 0.1.0
     */
    String exchange
  }

  /**
   * Information about where files are going to be located
   *
   * @since 0.1.0
   */
  static class Storage {

    /**
     * Templates path
     *
     * @since 0.1.0
     */
    String templates
  }
}
