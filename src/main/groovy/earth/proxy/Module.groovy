package earth.proxy

import com.google.inject.Scopes
import com.google.inject.AbstractModule

/**
 * Loads all Proxy services
 *
 * @since 0.1.0
 */
class Module extends AbstractModule {
  @Override
  void configure() {
    bind(ProxyService)
      .to(RibbonProxyService)
      .in(Scopes.SINGLETON)
  }
}
