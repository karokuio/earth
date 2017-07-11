package earth.templates

import ratpack.exec.Promise
import ratpack.form.UploadedFile
/**
 * Data storage interactions over the {@link Template} entity
 *
 * @since 0.1.0
 */
interface Repository {

  /**
   * Retrieves a list of templates
   *
   * @return a {@link Promise} with the {@link Template} instances
   * found
   * @since 0.1.0
   */
  Promise<List<Template>> list()

  /**
   * Deletes the {@link Template} identified by the id passed as
   * parameter
   *
   * @param id the {@link UUID} of the {@link Template} to be deleted
   * @return a {@link Promise} with the deleted {@link Template}
   * @since 0.1.0
   */
  Promise<Template> delete(UUID id)

  /**
   * Retrieves a specific template by its id
   *
   * @param id an {@link UUID} identifying the {@link Template}
   * instance
   * @return a {@link Promise} of a {@link Template} instance
   * @since 0.1.0
   */
  Promise<Template> findById(UUID id)

  /**
   *
   * @param template
   * @return
   * @since 0.1.0
   */
  Promise<Template> insert(Template template)
}
