package earth.templates

import ratpack.exec.Promise

/**
 * Operations over {@link Template} entity
 *
 * @since 0.1.0
 */
interface Service {

  /**
   * Stores the template information in database and sends the
   * template files to the shared folders
   *
   * @param is {@link InputStream} with the content of the zip file
   * @param template the template's basic info
   * @return a {@link Promise} with the template's basic information
   * @since 0.1.0
   */
  Promise<Template> createTemplate(InputStream is, Template template)

  /**
   * Deletes a given {@link Template}
   *
   * @param uuid data store id
   * @return a promise with the deleted {@link Template}
   * @since 0.1.0
   */
  Promise<Template> deleteTemplate(UUID uuid)
}
