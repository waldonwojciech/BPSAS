package pl.wojciechwaldon.bpsas.application.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.wojciechwaldon.bpsas.application.exception.user.UserNotFoundException;
import pl.wojciechwaldon.bpsas.domain.model.user.User;
import pl.wojciechwaldon.bpsas.domain.model.user.company.Company;
import pl.wojciechwaldon.bpsas.domain.model.user.naturalperson.NaturalPerson;
import pl.wojciechwaldon.bpsas.domain.repository.user.UserRepository;
import pl.wojciechwaldon.bpsas.domain.repository.user.company.CompanyRepository;
import pl.wojciechwaldon.bpsas.domain.repository.user.naturalperson.NaturalPersonRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private NaturalPersonRepository naturalPersonRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private UserRepository userRepository;

    public List<User> searchUser(String searchPhrase) {
        return getFoundUsers(searchPhrase);
    }

    public User updateUser(User user) {
        return userRepository.save(user);
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

    private List<User> getFoundUsers(String searchPhrase) {
        List<User> foundUsers = new ArrayList<>();
        List<NaturalPerson> naturalPersons = new ArrayList<>();
        List<Company> companies = new ArrayList<>();

        Optional<Set<NaturalPerson>> naturalPersonRepoResultOptional = naturalPersonRepository.findByFirstNameStartingWith(searchPhrase);
        if (naturalPersonRepoResultOptional.isPresent())
            naturalPersons = addFoundNaturalPersons(naturalPersons, (List<NaturalPerson>) naturalPersonRepoResultOptional.get());

        naturalPersonRepoResultOptional = naturalPersonRepository.findByLastNameStartingWith(searchPhrase);
        if (naturalPersonRepoResultOptional.isPresent())
            naturalPersons = addFoundNaturalPersons(naturalPersons, (List<NaturalPerson>) naturalPersonRepoResultOptional.get());

        Optional<Set<Company>> companiesOptional = companyRepository.findByCompanyNameStartingWith(searchPhrase);
        if (companiesOptional.isPresent())
            companies = (List<Company>) companiesOptional.get();

        foundUsers.addAll(naturalPersons);
        foundUsers.addAll(companies);

        return foundUsers;
    }

    private List<NaturalPerson> addFoundNaturalPersons(List<NaturalPerson> foundNaturalPersons, List<NaturalPerson> naturalPersons) {
        List<NaturalPerson> uniqueResults = foundNaturalPersons;
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
