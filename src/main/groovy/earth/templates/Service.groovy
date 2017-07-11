package earth.templates

import java.nio.ByteBuffer
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
}
