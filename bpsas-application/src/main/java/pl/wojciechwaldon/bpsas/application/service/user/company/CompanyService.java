package pl.wojciechwaldon.bpsas.application.service.user.company;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.wojciechwaldon.bpsas.application.exception.user.company.CompanyAlreadyExistException;
import pl.wojciechwaldon.bpsas.application.exception.user.company.CompanyNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.wojciechwaldon.bpsas.application.exception.user.naturalperson.NaturalPersonAlreadyExistException;
import pl.wojciechwaldon.bpsas.application.service.EmailAvaibilityValidator;
import pl.wojciechwaldon.bpsas.domain.model.user.company.Company;
import pl.wojciechwaldon.bpsas.domain.model.user.naturalperson.NaturalPerson;
import pl.wojciechwaldon.bpsas.domain.repository.user.company.CompanyRepository;

import java.util.Optional;
import java.util.Set;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private EmailAvaibilityValidator emailAvaibilityValidator;

    public ResponseEntity<Company> postCompany(Company company) {
        String email = company.getEmail();
        if(emailAvaibilityValidator.isEmailAvaible(email)) {
            companyRepository.save(company);

            return new ResponseEntity<Company>(HttpStatus.CREATED);
        }
        throw new CompanyAlreadyExistException();
    }

    public Company getCompanyByCompanyName(String companyName) {
        return validateReceivedResult(companyRepository.findByCompanyName(companyName));
    }

    public Company getCompanyByEmail(String email) {
        return validateReceivedResult(companyRepository.findByEmail(email));
    }

    private Set<Company> validateReceivedResults(Optional<Set<Company>> companies) {
        if (companies.isPresent())
            throw new CompanyNotFoundException();
        else
            return companies.get();
    }

    private Company validateReceivedResult(Optional<Company> company) {
        if (company.isPresent())
            return company.get();
        else
            throw new CompanyNotFoundException();
    }
}
