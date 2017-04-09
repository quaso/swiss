package org.swiss.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.swiss.repository.impl.TournamentRepositoryImpl;

@Configuration
// @EnableJpaRepositories(basePackageClasses = TournamentRepository.class)
// @EntityScan(basePackageClasses = Tournament.class)
// @EnableTransactionManagement
@ComponentScan(basePackageClasses = TournamentRepositoryImpl.class)
public class DatasourceConfiguration {

}
