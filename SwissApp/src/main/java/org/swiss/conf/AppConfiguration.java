package org.swiss.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.swiss.configuration.DatasourceConfiguration;
import org.swiss.endpoint.MatchEndpoint;
import org.swiss.service.MatchService;
import org.swiss.util.PlayerComparator;
import org.swiss.util.RoundPredicter;

@Configuration
@Import({ DatasourceConfiguration.class })
@ComponentScan(basePackageClasses = { MatchService.class, MatchEndpoint.class })
public class AppConfiguration {

	@Bean
	public PlayerComparator playerComparator() {
		return new PlayerComparator();
	}

	@Bean
	public RoundPredicter roundPredicter() {
		return new RoundPredicter();
	}
}
