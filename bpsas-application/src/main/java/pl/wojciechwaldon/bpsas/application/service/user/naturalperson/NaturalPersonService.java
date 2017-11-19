package pl.wojciechwaldon.bpsas.application.service.user.naturalperson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.wojciechwaldon.bpsas.application.exception.user.naturalperson.NaturalPersonAlreadyExistException;
import pl.wojciechwaldon.bpsas.application.exception.user.naturalperson.NaturalPersonNotFoundException;
import pl.wojciechwaldon.bpsas.domain.model.user.naturalperson.NaturalPerson;
import pl.wojciechwaldon.bpsas.domain.repository.user.naturalperson.NaturalPersonRepository;

import java.util.Optional;
import java.util.Set;

@Service
public class NaturalPersonService {

    @Autowired
    private NaturalPersonRepository naturalPersonRepository;

    public NaturalPerson getNaturalPersonByEmail(String email) {
        return validateReceivedResult(naturalPersonRepository.findByEmail(email));
    }


    public ResponseEntity<NaturalPerson> postNaturalPerson(NaturalPerson naturalPerson) {
        String email = naturalPerson.getEmail();
        if(isEmailAvaible(email)) {
            naturalPersonRepository.save(naturalPerson);

            return new ResponseEntity<NaturalPerson>(HttpStatus.CREATED);
        }
        throw new NaturalPersonAlreadyExistException();
    }

    private boolean isEmailAvaible(String email) {
        return !naturalPersonRepository.findByEmail(email).isPresent();
    }


    private Set<NaturalPerson> validateReceivedResults(Optional<Set<NaturalPerson>> naturalPersones) {
        if (naturalPersones.isPresent())
            throw new NaturalPersonNotFoundException();
        else
            return naturalPersones.get();
    }

    private NaturalPerson validateReceivedResult(Optional<NaturalPerson> naturalPerson) {
        if (naturalPerson.isPresent())
            return naturalPerson.get();
        else
            throw new NaturalPersonNotFoundException();
    }
}
