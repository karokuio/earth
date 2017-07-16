package earth.events

/**
 * Reference for all events sent/received by EARTH module
 *
 * @since 0.1.0
 */
class Events {

  /**
   * All events produced by Karoku
   *
   * @since 0.1.0
   */
  static final String ALL = 'event.#'

  /**
   * All Docker related events
   *
   * @since 0.1.0
   */
  static final String DOCKER_ALL = '*.docker.#'

  /**
   * All Karoku template's related events
   *
   * @since 0.1.0
   */
  static final String TEMPLATE_ALL = '*.template.*'

  /**
   * Event produced when a new template has been registered in the
   * database
   *
   * @since 0.1.0
   */
  static final String TEMPLATE_CREATED = 'event.template.created'

  /**
   * Event produced when an existing template has been deleted from
   * the database
   *
   * @since 0.1.0
   */
  static final String TEMPLATE_DELETED = 'event.template.deleted'

  /**
   * Event produced when a web hook from Github has been triggered
   *
   * @since 0.1.0
   */
  static final String WHOOK_GITHUB_REQUESTED = 'event.wbook.github.requested'
}
