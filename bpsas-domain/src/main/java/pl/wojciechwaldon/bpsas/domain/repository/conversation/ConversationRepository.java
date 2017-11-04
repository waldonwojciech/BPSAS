package pl.wojciechwaldon.bpsas.domain.repository.conversation;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.wojciechwaldon.bpsas.domain.model.conversation.Conversation;

public interface ConversationRepository extends CrudRepository<Conversation, Long> {
}
