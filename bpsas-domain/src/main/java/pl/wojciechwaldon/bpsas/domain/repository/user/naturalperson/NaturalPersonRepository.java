package pl.wojciechwaldon.bpsas.domain.repository.user.naturalperson;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.wojciechwaldon.bpsas.domain.model.user.naturalperson.NaturalPerson;

import java.util.Optional;
import java.util.Set;

@Repository
public interface NaturalPersonRepository extends CrudRepository<NaturalPerson, String> {

    Optional<NaturalPerson> findByEmail(String email);

    Optional<Set<NaturalPerson>> findByFirstName(String firstName);

    Optional<Set<NaturalPerson>> findByLastName(String lastName);

    Optional<Set<NaturalPerson>> findByFirstNameStartingWithAndLastNameStartingWith(String firstName, String lastName);

    Optional<Set<NaturalPerson>> findByFirstNameStartingWith(String firstName);

    Optional<Set<NaturalPerson>> findByLastNameStartingWith(String lastName);

}
