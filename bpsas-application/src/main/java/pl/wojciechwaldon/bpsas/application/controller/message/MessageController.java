package pl.wojciechwaldon.bpsas.application.controller.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.wojciechwaldon.bpsas.application.exception.message.MessageNotFoundException;
import pl.wojciechwaldon.bpsas.application.service.message.MessageService;
import pl.wojciechwaldon.bpsas.domain.model.message.Message;

import java.util.List;

@Controller
@RequestMapping(value = "/message")
public class MessageController {

    private static Logger logger = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    private MessageService messageService;

    @RequestMapping(value = "/findByConversationId", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Message> getMessagesByConversationId(@RequestParam(value = "conversationId") String conversationId)
    throws MessageNotFoundException{
        return messageService.getMessagesByConversationId(Long.decode(conversationId));
    }
}
