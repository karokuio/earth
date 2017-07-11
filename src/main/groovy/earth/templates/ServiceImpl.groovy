package earth.templates

import javax.inject.Inject
import ratpack.exec.Result
import ratpack.exec.Promise
import java.nio.file.Paths
import earth.notifiers.Notifier
import earth.storage.StorageService
import earth.util.Events
import earth.Config

/**
 * Default implementation of the {@link Service} interface
 *
 * @since 0.1.0
 */
class ServiceImpl implements Service {

  static final TEMPLATE_FILE_SUFFIX = '.zip'

  @Inject
  CassandraRepository repository

  @Inject
  StorageService storage

  @Inject
  Notifier notifier

  @Inject
  Config config

  @Override
  Promise<Template> createTemplate(InputStream is, Template template) {
    is.reset()

    return storage
      .store(is, Paths.get(config.storage.templates, "${template.tag}${TEMPLATE_FILE_SUFFIX}"))
      .flatMap {
        repository.insert(template)
      }.wiretap {
        notifier.event("templates.created",
                       Events.templateCreated(
                         template.copyWith(id: UUID.randomUUID())))
      }
  }

  @Override
  Promise<Template> deleteTemplate(UUID uuid) {
    return repository
      .delete(uuid)
      .wiretap { Result<Template> result ->
        notifier.event("templates.deleted", Events.templateDeleted(result.value))
      }
  }
}
