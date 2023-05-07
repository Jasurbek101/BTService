package uz.BTService.btservice.config;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class ApplicationRunner implements CommandLineRunner {
    private static final Logger LOG = Logger.getLogger(ApplicationRunner.class.getName());
    @Autowired
    private DataSourceConfig dataSourceConfig;

    @Override
    public void run(String... args) throws Exception {
        System.out.println(String.format("%s => ### Flyway migration ###", this.getClass()));
        // Create the Flyway instance and point it to the database
        Flyway flyway = Flyway.configure()
                .dataSource(dataSourceConfig.getUrl(), dataSourceConfig.getUsername(), dataSourceConfig.getPassword())
                .baselineOnMigrate(true)
                .sqlMigrationPrefix("C")
                .baselineVersion("0")
                .outOfOrder(true)
                .load();
        // Start the migration
        flyway.migrate();
    }
}