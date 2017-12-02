package pl.wojciechwaldon.bpsas.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import pl.wojciechwaldon.bpsas.domain.repository.user.company.CompanyRepository;
import pl.wojciechwaldon.bpsas.domain.repository.user.naturalperson.NaturalPersonRepository;

@Component
public class EmailAvaibilityValidator {

    @Autowired
    private NaturalPersonRepository naturalPersonRepository;

    @Autowired
    private CompanyRepository companyRepository;

    public boolean isEmailAvaible(String email) {
        return !naturalPersonRepository.findByEmail(email).isPresent() && !companyRepository.findByEmail(email).isPresent();
    }
}
