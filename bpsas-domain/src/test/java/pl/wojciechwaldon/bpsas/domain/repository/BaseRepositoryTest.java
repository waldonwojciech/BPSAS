package pl.wojciechwaldon.bpsas.domain.repository;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import pl.wojciechwaldon.bpsas.domain.TestSpringBootApplicationClass;
import pl.wojciechwaldon.bpsas.domain.model.announcement.Announcement;
import pl.wojciechwaldon.bpsas.domain.model.conversation.Conversation;
import pl.wojciechwaldon.bpsas.domain.model.message.Message;
import pl.wojciechwaldon.bpsas.domain.model.user.User;
import pl.wojciechwaldon.bpsas.domain.model.user.naturalperson.NaturalPerson;
import pl.wojciechwaldon.bpsas.domain.repository.announcement.AnnouncementRepository;
import pl.wojciechwaldon.bpsas.domain.repository.conversation.ConversationRepository;
import pl.wojciechwaldon.bpsas.domain.repository.message.MessageRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BaseRepositoryTest {

    @Autowired
    protected ConversationRepository conversationRepository;

    @Autowired
    protected MessageRepository messageRepository;

    @Autowired
    protected AnnouncementRepository announcementRepository;

    protected final String TEST_MESSAGE_CONTENT = "test-test-message-content";
    protected final String TEST_EMAIL = "test-email";
    protected final String TEST_PASSWORD = "test-password";

    protected Message test_message;
    protected Conversation test_conversation;
    protected Announcement test_announcement;

    protected List<Message> test_messages = new ArrayList<Message>();
    protected Set<Conversation> test_conversations = new HashSet<Conversation>();
    protected Set<User> test_users = new HashSet<User>();
    protected Set<Announcement> test_announcements = new HashSet<Announcement>();


    protected void prepareMessage() {
        test_message = new Message.Builder()
                .withConversation(test_conversation)
                .withContent(TEST_MESSAGE_CONTENT)
                .build();

        test_messages.add(test_message);
    }

    protected void prepareAnnouncement() {
        test_announcement = new Announcement.Builder()
                .withUsers(test_users)
                .build();

        test_announcements.add(test_announcement);
    }

    protected void prepareConversation() {
        test_conversation = new Conversation.Builder()
                .withUsers(test_users)
                .withMessages(test_messages)
                .build();

        prepareMessage();
    }
}
