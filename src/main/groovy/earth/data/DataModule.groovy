package earth.data

import com.datastax.driver.core.Cluster
import com.google.inject.AbstractModule
import com.google.inject.Scopes

/**
 * @since 0.1.0
 */
class DataModule extends AbstractModule {
    @Override
    void configure() {
        bind(Cluster)
            .toProvider(ClusterProvider)
            .in(Scopes.SINGLETON)

    }
}
