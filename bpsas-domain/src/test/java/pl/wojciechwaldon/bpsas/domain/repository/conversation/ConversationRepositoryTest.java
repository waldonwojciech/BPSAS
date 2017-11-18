package pl.wojciechwaldon.bpsas.domain.repository.conversation;

import org.junit.After;
import org.junit.Test;
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
import pl.wojciechwaldon.bpsas.domain.repository.BaseRepositoryTest;
import pl.wojciechwaldon.bpsas.domain.repository.announcement.AnnouncementRepository;
import pl.wojciechwaldon.bpsas.domain.repository.message.MessageRepository;
import pl.wojciechwaldon.bpsas.domain.repository.user.UserRepository;
import pl.wojciechwaldon.bpsas.domain.repository.user.naturalperson.NaturalPersonRepository;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestSpringBootApplicationClass.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class})
@WebAppConfiguration
public class ConversationRepositoryTest extends BaseRepositoryTest{

    @Autowired
    private NaturalPersonRepository naturalPersonRepository;

    @Autowired
    private UserRepository userRepository;

    private final String TEST_EMAIL_SENDER = "test-email-sender";
    private final String TEST_EMAIL_RECEIVER = "test-email-receiver";
    private final String TEST_PASSWORD = "test-password";
    private final String TEST_FIRST_NAME = "test-first-name";
    private final String TEST_LAST_NAME = "test-last-name";
    private final String TEST_ADDING_MESSAGE_CONTENT = "test-adding-message-content";

    private Message test_adding_message;
    private Set<User> test_users = new HashSet<User>();


    @After
    public void tearDown() {
        conversationRepository.deleteAll();
        naturalPersonRepository.deleteAll();
    }

    @Test
    public void shouldPersistConversationWithExistingSenderAndReciever() {
        //given
        prepareSenderAndReciever();
        prepareConversation();

        //when
        test_conversation = conversationRepository.save(test_conversation);

        //then
        assertThat(conversationRepository.findById(test_conversation.getId()).get()).isEqualTo(test_conversation);
        assertThat(conversationRepository.count()).isEqualTo(1);
        assertThat(naturalPersonRepository.count()).isEqualTo(2);
    }

    @Test
    public void shouldUpdateConversation() {
        //given
        prepareSenderAndReciever();
        prepareConversation();
        conversationRepository.save(test_conversation);

        //when
        test_conversation.setUsers(new HashSet<>());
        conversationRepository.save(test_conversation);

        //then
        assertThat(conversationRepository.findById(test_conversation.getId()).get()).isEqualTo(test_conversation);
        assertThat(conversationRepository.count()).isEqualTo(1);
        assertThat(messageRepository.count()).isEqualTo(1);
        assertThat(naturalPersonRepository.count()).isEqualTo(2);
    }

    @Test
    public void shouldDeleteConversationWithMessages() {
        //given
        prepareSenderAndReciever();
        prepareConversation();
        test_conversation = conversationRepository.save(test_conversation);

        //when
        conversationRepository.delete(test_conversation);

        //then
        assertThat(conversationRepository.findById(test_conversation.getId())).isEqualTo(Optional.empty());
        assertThat(messageRepository.findById(test_messages.get(0).getId())).isEqualTo(Optional.empty());
        assertThat(naturalPersonRepository.count()).isEqualTo(2);

    }

    @Test
    public void shouldAddMessageToConversation() {
        //given
        prepareConversation();
        prepareAddingMessage();

        //when
        test_conversation.getMessages().add(test_adding_message);
        test_conversation = conversationRepository.save(test_conversation);

        Conversation fetchetConversaction = conversationRepository.findById(test_conversation.getId()).get();
        Message fetchedMessage = messageRepository.findById(test_adding_message.getId()).get();

        //then
        assertThat(fetchetConversaction).isEqualTo(test_conversation);
        assertThat(fetchedMessage).isEqualTo(test_adding_message);
        assertThat(conversationRepository.count()).isEqualTo(1);
        assertThat(messageRepository.count()).isEqualTo(2);
    }

    private void prepareAddingMessage() {
        test_adding_message = new Message.Builder()
                .withConversation(test_conversation)
                .withContent(TEST_ADDING_MESSAGE_CONTENT)
                .build();
    }

    private void prepareSenderAndReciever() {
        User sender = new NaturalPerson.Builder()
                .withEmail(TEST_EMAIL_SENDER)
                .withPassword(TEST_PASSWORD)
                .withFirstName(TEST_FIRST_NAME)
                .withLastName(TEST_LAST_NAME)
                .withAnnouncements(test_announcements)
                .withConversations(test_conversations)
                .build();
        User receiver = new NaturalPerson.Builder()
                .withEmail(TEST_EMAIL_RECEIVER)
                .withPassword(TEST_PASSWORD)
                .withFirstName(TEST_FIRST_NAME)
                .withLastName(TEST_LAST_NAME)
                .withAnnouncements(test_announcements)
                .withConversations(test_conversations)
                .build();
        test_users = new HashSet<>();
        test_users.add(sender);
        test_users.add(receiver);

        userRepository.saveAll(test_users);
    }
}