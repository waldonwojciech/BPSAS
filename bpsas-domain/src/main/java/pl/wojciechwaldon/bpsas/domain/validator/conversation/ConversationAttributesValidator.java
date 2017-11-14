package pl.wojciechwaldon.bpsas.domain.validator.conversation;

import org.apache.commons.lang3.Validate;
import pl.wojciechwaldon.bpsas.domain.model.conversation.Conversation;

public class ConversationAttributesValidator {

    public static void validate(Conversation conversation) {
        Validate.notNull(conversation.getUsers(), "Users cannot be null.");
        Validate.notNull(conversation.getMessages(), "Messages cannot be null.");
    }
}
