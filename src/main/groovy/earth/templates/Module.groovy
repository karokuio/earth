package earth.templates

import com.google.inject.Scopes
import com.google.inject.AbstractModule

/**
 * Loads all required templates related services
 *
 * @since 0.1.0
 */
class Module extends AbstractModule {
  @Override
  void configure() {
    bind(Repository)
      .to(CassandraRepository)
      .in(Scopes.SINGLETON)
  }
}
