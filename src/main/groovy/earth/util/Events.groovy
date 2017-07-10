package earth.util

import earth.notifiers.Event
import earth.templates.Template

/**
 * Utility class as a shortcut to create system events
 *
 * @since 0.1.0
 */
class Events {

  /**
   * Creates an event when a template is created
   *
   * @param template the template created
   * @return an instance of {@link Event}
   * @since 0.1.0
   */
  static Event templateCreated(final Template template) {
    return new Event("templates/created", template)
  }
}
