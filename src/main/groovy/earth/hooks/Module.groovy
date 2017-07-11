package earth.hooks

import com.google.inject.Scopes
import com.google.inject.AbstractModule

/**
 * Loads all
 *
 * @since 0.1.0
 */
class Module extends AbstractModule {
  @Override
  void configure() {
    bind(Service)
      .to(ServiceImpl)
      .in(Scopes.SINGLETON)
  }
}
