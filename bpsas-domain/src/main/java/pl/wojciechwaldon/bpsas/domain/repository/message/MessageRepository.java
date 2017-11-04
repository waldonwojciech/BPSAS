package pl.wojciechwaldon.bpsas.domain.repository.message;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.wojciechwaldon.bpsas.domain.model.message.Message;

public interface MessageRepository extends CrudRepository<Message, Long> {
}
