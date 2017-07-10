package earth.data

import com.datastax.driver.core.ResultSet
import com.datastax.driver.core.Row
import com.datastax.driver.core.Session
import com.datastax.driver.core.Cluster
import com.google.common.util.concurrent.ListenableFuture
import ratpack.exec.Downstream
import ratpack.exec.Promise
import ratpack.exec.Upstream

/**
 * Cassandra related functions
 *
 * @since 0.1.0
 */
class Cassandra {

  /**
   * Executes a Cassandra's query asynchronously and closes the used
   * session once the promise has been resolved
   *
   * @param cluster Cassandra's cluster pointer
   * @param stmt query statement
   * @param args query arguments
   * @return a {@link ResultSet} promise
   * @since 0.1.0
   */
  static Promise<ResultSet> executeAsync(final Cluster cluster, final String stmt, final Object...args) {
    final Session session = cluster.connect()

    return Promise
      .async(accept(session.executeAsync(stmt, args)))
      .close(session)
  }

  /**
   * Generates {@link UUID} ids
   *
   * @return an instance of {@link UUID}
   * @since 0.1.0
   */
  static UUID generateUUID() {
    return UUID.randomUUID()
  }

  /**
   * Returns a single {@link Row} from the instance of {@link
   * ResultSet} passed as argument
   *
   * @param rs instance of {@link ResultSet}
   * @return the first row out of the {@link ResultSet} passed as parameter
   * @since 0.1.0
   */
  static Row singleRow(ResultSet rs) {
    return rs.one()
  }

  private static <T> Upstream<T> accept(final ListenableFuture<T> listenable) {
    return { Downstream<T> down ->
      down.accept(listenable)
    } as Upstream<T>
  }
}
