package earth.templates

import static ratpack.jackson.Jackson.json

import java.nio.ByteBuffer

import ratpack.handling.Context
import ratpack.form.Form
import ratpack.form.UploadedFile

import earth.util.Zip
import earth.util.Templates

/**
 * Request handlers over the {@link Template} entity
 *
 * @since 0.1.0
 */
class Handlers {

  static final String KAROKU_FILE = 'karoku.yaml'

  /**
   * List of available {@link Template} instances
   *
   * @param ctx Ratpack's context
   * @since 0.1.0
   */
  static void list(final Context ctx) {
    Repository repository = ctx.get(Repository)

    repository
      .list()
      .then { List<Template> templates ->
        ctx.render(json(templates))
      }
  }

  /**
   * @param ctx
   * @since 0.1.0
   */
  static void insert(final Context ctx) {
    Service service = ctx.get(Service)

    ctx
      .parse(Form)
      .flatMap { Form form ->
        UploadedFile file = form.file('file')
        InputStream is = file.inputStream
        Template template = Templates.parse(Zip.toText(is, KAROKU_FILE))

        service.createTemplate(is, template)
      }.then { Template template ->
        ctx.render(json(template))
      }
  }

  /**
   * @param ctx
   * @since 0.1.0
   */
  static void delete(final Context ctx) {
    Service service = ctx.get(Service)

    ctx
      .parse(Map)
      .flatMap {
        service.deleteTemplate(UUID.fromString("${it.id}"))
      }.then { Template template ->
        ctx.render(json(template))
      }
  }
}
