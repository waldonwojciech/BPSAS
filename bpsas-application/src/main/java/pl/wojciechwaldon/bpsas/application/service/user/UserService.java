package pl.wojciechwaldon.bpsas.application.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.wojciechwaldon.bpsas.application.exception.user.UserNotFoundException;
import pl.wojciechwaldon.bpsas.domain.model.user.User;
import pl.wojciechwaldon.bpsas.domain.model.user.company.Company;
import pl.wojciechwaldon.bpsas.domain.model.user.naturalperson.NaturalPerson;
import pl.wojciechwaldon.bpsas.domain.repository.user.company.CompanyRepository;
import pl.wojciechwaldon.bpsas.domain.repository.user.naturalperson.NaturalPersonRepository;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private NaturalPersonRepository naturalPersonRepository;

    @Autowired
    private CompanyRepository companyRepository;

    public ResponseEntity<User> loginUser(User user) {
        String email = user.getEmail();
        Optional<NaturalPerson> naturalPerson = naturalPersonRepository.findByEmail(email);
        Optional<Company> company = companyRepository.findByEmail(email);
        if (naturalPerson.isPresent())
            return new ResponseEntity(naturalPerson, HttpStatus.FOUND);
        else if (company.isPresent())
            return new ResponseEntity(naturalPerson, HttpStatus.FOUND);
        else
            throw new UserNotFoundException();
    }

}
