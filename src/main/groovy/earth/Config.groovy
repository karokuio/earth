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

    /**
     * List of
     *
     * @since 0.1.0
     */
    List<String> pipes

    String exchange
  }

  static class Storage {

    String templates
  }
}
