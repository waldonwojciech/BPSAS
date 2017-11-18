package pl.wojciechwaldon.bpsas.domain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan("pl.wojciechwaldon.bpsas.domain.model")
@EnableJpaRepositories("pl.wojciechwaldon.bpsas.domain.repository")
@EnableScheduling
public class TestSpringBootApplicationClass {

    public static void main(String[] args) {
        SpringApplication.run(TestSpringBootApplicationClass.class, args);
    }
}
