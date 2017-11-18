package pl.wojciechwaldon.bpsas.domain.repository.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.wojciechwaldon.bpsas.domain.model.user.User;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
}
