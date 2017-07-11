package earth.templates

import javax.inject.Inject
import ratpack.exec.Promise
import ratpack.form.UploadedFile
import com.datastax.driver.core.Row
import com.datastax.driver.core.Cluster
import com.datastax.driver.core.ResultSet

import earth.util.Events
import earth.data.Cassandra
import earth.notifiers.Notifier

/**
 * {@link Repository} implementation over an underlying Cassandra data
 * store
 *
 * @since 0.1.0
 */
class CassandraRepository implements Repository {

  /**
   * Cassandra's cluster pointer
   *
   * @since 0.1.0
   */
  @Inject
  Cluster cluster

  @Inject
  Notifier notifier

  @Override
  Promise<List<Template>> list() {
    final String query = "SELECT * FROM earth.templates"

    return Cassandra
      .executeAsync(cluster, query)
      .map { ResultSet rs ->
        rs.all().collect(this.&toTemplate) as List<Template>
      }
  }

  @Override
  Promise<Template> findById(UUID id) {
    final String query = "SELECT * FROM earth.templates WHERE id = ?"

    return Cassandra
      .executeAsync(cluster, query, id)
      .map(Cassandra.&singleRow)
      .map(this.&toTemplate)
  }

  @Override
  Promise<Template> delete(final UUID id) {
    final String query = "DELETE FROM earth.templates WHERE id = ?"

    return findById(id)
      .flatMap { Template template ->
        Cassandra
          .executeAsync(cluster, query, template.id)
          .map { template }
      }
  }

  @Override
  Promise<Template> insert(final Template template) {
    final UUID uuid = Cassandra.generateUUID()
    final String query = """
      INSERT INTO earth.templates
        (id, description, template)
      VALUES
        (?, ?, ?)
    """

    return Cassandra
    .executeAsync(cluster,
                  query,
                  uuid,
                  template.description,
                  template.template)
    .wiretap {
      notifier.event('templates', Events.templateCreated(template.copyWith(id: uuid)))
    }.flatMap {
      findById(uuid)
    }
  }

  private Template toTemplate(final Row row) {
    return new Template(
      id: row.getUUID('id'),
      description: row.getString('description'),
      template: row.getString('template'),
    )
  }
}
