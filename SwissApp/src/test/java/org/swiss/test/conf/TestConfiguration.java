package org.swiss.test.conf;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.swiss.conf.AppConfiguration;

@SpringBootConfiguration
@EnableAutoConfiguration
@Import(AppConfiguration.class)
public class TestConfiguration {

}
