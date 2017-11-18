package pl.wojciechwaldon.bpsas.application.service.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.wojciechwaldon.bpsas.application.exception.message.MessageNotFoundException;
import pl.wojciechwaldon.bpsas.domain.model.message.Message;
import pl.wojciechwaldon.bpsas.domain.repository.message.MessageRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public List<Message> getMessagesByConversationId(Long conversationId) {
        return validateReceivedResults(messageRepository.findMessageByConversationId(conversationId));
    }

    private List<Message> validateReceivedResults(Optional<List<Message>> messages) {
        if (messages.get().isEmpty())
            throw new MessageNotFoundException();
        else
            return messages.get();
    }

    private Message validateReceivedResult(Optional<Message> message) {
        if (message.isPresent())
            return message.get();
        else
            throw new MessageNotFoundException();
    }
}
