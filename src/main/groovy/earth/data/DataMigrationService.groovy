package earth.data

import javax.inject.Inject
import com.datastax.driver.core.Cluster
import org.cognitor.cassandra.migration.Database
import org.cognitor.cassandra.migration.MigrationTask
import org.cognitor.cassandra.migration.MigrationRepository
import org.cognitor.cassandra.migration.keyspace.Keyspace

/**
 * Triggers the Cassandra data migration tool
 *
 * @since 0.1.2
 */
class DataMigrationService {

    /**
     * Earth keyspace
     *
     * @since 0.1.2
     */
    static final String EARTH_KEYSPACE = 'earth'

    /**
     * Query to drop Earth keyspace in Cassandra
     * @since 0.1.2
     */
    static final String EARTH_DROP_KEYSPACE = """
        DROP KEYSPACE $EARTH_KEYSPACE
    """

    /**
     * Cassandra cluster connection
     *
     * @since 0.1.2
     */
    @Inject
    Cluster cluster

    /**
     * Launches the migration process
     *
     * @since 0.1.2
     */
    void migrate() {
        Keyspace earthKeyspace = new Keyspace(EARTH_KEYSPACE)
        Database databaseConnection = new Database(cluster, earthKeyspace)
        MigrationTask migrationTask = new MigrationTask(databaseConnection, new MigrationRepository())

        migrationTask.migrate()
    }

    /**
     * Drops the keyspace. Only used in testing environment
     *
     * @since 0.1.2
     */
    void dropKeyspace() {
        cluster.connect().execute(EARTH_DROP_KEYSPACE)
    }
}
