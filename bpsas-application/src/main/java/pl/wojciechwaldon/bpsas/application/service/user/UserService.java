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

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private NaturalPersonRepository naturalPersonRepository;

    @Autowired
    private CompanyRepository companyRepository;

    public Set<User> searchUser(String searchPhrase) {
        return getFoundUsers(searchPhrase);
    }

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

    private Set<User> getFoundUsers(String searchPhrase) {
        Set<User> foundUsers = new HashSet<>();
        Set<NaturalPerson> naturalPersons = new HashSet<>();
        Set<Company> companies;

        Set<NaturalPerson> naturalPersonRepoResult = naturalPersonRepository.findByFirstNameStartingWith(searchPhrase).get();
        naturalPersons = addFoundNaturalPersons(naturalPersons, naturalPersonRepoResult);
        naturalPersonRepoResult = naturalPersonRepository.findByLastNameStartingWith(searchPhrase).get();
        naturalPersons = addFoundNaturalPersons(naturalPersons, naturalPersonRepoResult);

        companies = companyRepository.findByCompanyNameStartingWith(searchPhrase).get();

        foundUsers.add((User) naturalPersons);
        foundUsers.add((User) companies);

        return foundUsers;
    }

    private Set<NaturalPerson> addFoundNaturalPersons(Set<NaturalPerson> foundNaturalPersons, Set<NaturalPerson> naturalPersons) {
        Set<NaturalPerson> uniqueResults = foundNaturalPersons;
        for (NaturalPerson naturalPerson : naturalPersons) {
            boolean isUnique = true;
            for (NaturalPerson foundNaturalPerson : foundNaturalPersons) {
                if (foundNaturalPerson.getEmail().equalsIgnoreCase(naturalPerson.getEmail()))
                    isUnique = false;
            }
            if (isUnique)
                uniqueResults.add(naturalPerson);
        }

        return uniqueResults;
    }
}
