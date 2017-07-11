package earth.templates

import static ratpack.jackson.Jackson.json

import java.nio.ByteBuffer

import ratpack.handling.Context
import ratpack.form.Form
import ratpack.form.UploadedFile

import earth.util.Zip

/**
 * Request handlers over the {@link Template} entity
 *
 * @since 0.1.0
 */
class Handlers {

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
        String templateText = Zip.toText(is, 'karoku.yaml')
        is.reset()
        String description = Zip.toText(is, 'README.md')

        Template template =
          new Template(template: templateText,
                       description: description)

        is.reset()
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
    Repository repository = ctx.get(Repository)

    ctx
      .parse(Map)
      .flatMap {
        repository.delete(UUID.fromString("${it.id}"))
      }.then { Template template ->
        ctx.render(json(template))
      }
  }
}
