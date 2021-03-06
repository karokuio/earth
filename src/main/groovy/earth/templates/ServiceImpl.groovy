package earth.templates

import javax.inject.Inject
import ratpack.exec.Result
import ratpack.exec.Promise
import java.nio.file.Paths
import earth.events.Events
import earth.events.Publisher
import earth.storage.StorageService
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
  Publisher publisher

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
        publisher.publish(Events.TEMPLATE_CREATED, template.copyWith(id: UUID.randomUUID()))
      }
  }

  @Override
  Promise<Template> deleteTemplate(UUID uuid) {
    return repository
      .delete(uuid)
      .wiretap { Result<Template> result ->
        publisher.publish(Events.TEMPLATE_DELETED, result.value)
      }
  }
}
