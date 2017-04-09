package org.swiss.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Import;
import org.swiss.conf.AppConfiguration;

@SpringBootApplication
@Import(AppConfiguration.class)
public class SwissApplication extends SpringBootServletInitializer {

	public static void main(final String[] args) {
		SpringApplication.run(SwissApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(final SpringApplicationBuilder application) {
		return application.sources(SwissApplication.class);
	}
}
