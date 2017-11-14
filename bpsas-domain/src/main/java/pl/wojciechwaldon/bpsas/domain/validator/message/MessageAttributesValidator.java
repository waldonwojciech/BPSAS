package pl.wojciechwaldon.bpsas.domain.validator.message;

import org.apache.commons.lang3.Validate;
import pl.wojciechwaldon.bpsas.domain.model.message.Message;

public class MessageAttributesValidator {

    public static void validate(Message message) {
        Validate.notNull(message.getConversation(), "Conversation cannot be null.");
        Validate.notNull(message.getContent(), "Content cannot be null.");
    }
}
