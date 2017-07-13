package earth.templates

import javax.inject.Inject
import ratpack.exec.Promise
import com.datastax.driver.core.Row
import com.datastax.driver.core.Cluster
import com.datastax.driver.core.ResultSet

import earth.data.Cassandra

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
        (id, tag, description, template)
      VALUES
        (?, ?, ?, ?)
    """

    return Cassandra
    .executeAsync(cluster,
                  query,
                  uuid,
                  template.tag,
                  template.description,
                  template.template)
    .flatMap {
      findById(uuid)
    }
  }

  private Template toTemplate(final Row row) {
    return new Template(
      id: row.getUUID('id'),
      tag: row.getString('tag'),
      description: row.getString('description'),
      template: row.getString('template'),
    )
  }
}
