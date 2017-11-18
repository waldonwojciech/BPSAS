package pl.wojciechwaldon.bpsas.domain.repository.message;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.wojciechwaldon.bpsas.domain.model.message.Message;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface MessageRepository extends CrudRepository<Message, Long> {

    public Optional<List<Message>> findMessageByConversationId(Long id);
}
