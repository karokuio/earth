package earth.templates

import javax.inject.Inject
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
    return storage
      .store(is, Paths.get(config.storage.templates, "aloha.zip"))
      .flatMap {
        repository.insert(template)
      }.wiretap {
        notifier.event("templates/create",
                       Events.templateCreated(
                         template.copyWith(id: UUID.randomUUID())))
      }
  }
}
