package pl.wojciechwaldon.bpsas.application.exception.conversation;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="Conversation not found.")
public class ConversationNotFoundException extends RuntimeException{
}
