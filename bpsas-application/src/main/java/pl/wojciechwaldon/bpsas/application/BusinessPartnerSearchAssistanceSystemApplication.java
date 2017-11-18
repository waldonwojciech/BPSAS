package pl.wojciechwaldon.bpsas.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"pl.wojciechwaldon.bpsas.application.controller",
        "pl.wojciechwaldon.bpsas.application.service"})
@EntityScan(basePackages = "pl.wojciechwaldon.bpsas.domain.model")
@EnableJpaRepositories("pl.wojciechwaldon.bpsas.domain.repository")
public class BusinessPartnerSearchAssistanceSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(BusinessPartnerSearchAssistanceSystemApplication.class, args);
    }
}
