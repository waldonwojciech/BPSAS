package pl.wojciechwaldon.bpsas.application;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import pl.wojciechwaldon.bpsas.domain.file.StorageService;

import javax.annotation.Resource;

@SpringBootApplication
@ComponentScan(basePackages = {"pl.wojciechwaldon.bpsas.application.controller",
        "pl.wojciechwaldon.bpsas.application.service", "pl.wojciechwaldon.bpsas.domain.file"})
@EntityScan(basePackages = "pl.wojciechwaldon.bpsas.domain.model")
@EnableJpaRepositories("pl.wojciechwaldon.bpsas.domain.repository")
public class BusinessPartnerSearchAssistanceSystemApplication implements CommandLineRunner {

    @Resource
    private StorageService storageService;

    public static void main(String[] args) {
        SpringApplication.run(BusinessPartnerSearchAssistanceSystemApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        storageService.deleteAll();
        storageService.init();
    }
}
