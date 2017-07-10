package earth.templates

import groovy.transform.Immutable

/**
 * Represents an earth template. It has information on how to build or
 * deploy a a specific type of projects
 *
 * @since 0.1.0
 */
@Immutable(copyWith = true)
class Template implements Serializable {

  /**
   * Template's id
   *
   * @since 0.1.0
   */
  UUID id

  /**
   * Template's description
   *
   * @since 0.1.0
   */
  String description

  /**
   * Template's build/deploy information
   *
   * @since 0.1.0
   */
  String template
}
