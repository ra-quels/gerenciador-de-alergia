package br.cefet.ApiGerenciadorAlergia.config;

import lombok.extern.slf4j.Slf4j;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.statement.SqlLogger;
import org.jdbi.v3.core.statement.StatementContext;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.time.temporal.ChronoUnit;

@Slf4j
@Configuration
public class JdbiConfig {

    @Value("${jdbi.showsql}")
    private boolean showsql;

    @Bean
    DataSource driverManagerDataSource(
            @Value("${spring.datasource.url}") String url,
            @Value("${spring.datasource.username}") String username,
            @Value("${spring.datasource.password}") String password,
            @Value("${spring.datasource.driver-class-name}") String driverClassName) {

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setDriverClassName(driverClassName);
        return dataSource;
    }

    @Bean
    DataSourceTransactionManager dataSourceTransactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    Jdbi jdbi(DataSource dataSource) {

    	log.info("Show SQL is set to: {}", showsql);

        Jdbi jdbiRet = Jdbi.create(dataSource)
                .installPlugin(new SqlObjectPlugin());

        if (showsql) {
            SqlLogger sqlLogger = new SqlLogger() {
                @Override
                public void logAfterExecution(StatementContext context) {
                    log.info("JDBI.SQL: \n {} \n PARAMETERS: {} \n TIME: {} ms",
                            context.getRenderedSql(),
                            context.getBinding().toString(),
                            context.getElapsedTime(ChronoUnit.MILLIS));
                }
            };
            jdbiRet.setSqlLogger(sqlLogger);
        }

        return jdbiRet;
    }
}
