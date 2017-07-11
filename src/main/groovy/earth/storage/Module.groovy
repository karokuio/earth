package earth.storage

import com.google.inject.Scopes
import com.google.inject.AbstractModule

/**
 *
 *
 * @since 0.1.0
 */
class Module extends AbstractModule {
  @Override
  void configure() {
    bind(StorageService)
      .to(LocalStorageService)
      .in(Scopes.SINGLETON)
  }
}
