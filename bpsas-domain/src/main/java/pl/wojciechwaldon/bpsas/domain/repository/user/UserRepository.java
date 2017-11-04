package pl.wojciechwaldon.bpsas.domain.repository.user;

import org.springframework.data.repository.CrudRepository;
import pl.wojciechwaldon.bpsas.domain.model.user.User;
import pl.wojciechwaldon.bpsas.domain.model.user.naturalperson.NaturalPerson;

public interface UserRepository extends CrudRepository<User, String>{
}
