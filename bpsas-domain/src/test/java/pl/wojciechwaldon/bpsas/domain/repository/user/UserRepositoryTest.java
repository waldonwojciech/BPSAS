package pl.wojciechwaldon.bpsas.domain.repository.user;

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
import pl.wojciechwaldon.bpsas.domain.model.user.company.Company;
import pl.wojciechwaldon.bpsas.domain.model.user.naturalperson.NaturalPerson;
import pl.wojciechwaldon.bpsas.domain.repository.announcement.AnnouncementRepository;
import pl.wojciechwaldon.bpsas.domain.repository.conversation.ConversationRepository;
import pl.wojciechwaldon.bpsas.domain.repository.message.MessageRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestSpringBootApplicationClass.class })
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class })
@WebAppConfiguration
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConversationRepository conversationRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private AnnouncementRepository announcementRepository;

    private final String TEST_EMAIL = "test-email";
    private final String TEST_PASSWORD = "test-password";
    private final String TEST_FIRST_NAME = "test-first-name";
    private final String TEST_LAST_NAME = "test-last-name";
    private final String TEST_MESSAGE_CONTENT = "test-test-message-content";
    private final String TEST_COMPANY_NAME ="test-company-name";

    private Conversation test_conversation;
    private User test_naturalPerson;
    private User test_company;
    private Message test_message;
    private Announcement test_announcement;
    private List<Message> test_messages = new ArrayList<Message>();
    private Set<Conversation> test_conversations = new HashSet<Conversation>();
    private Set<User> test_users = new HashSet<User>();
    private Set<Announcement> test_announcements = new HashSet<Announcement>();

    @After
    public void tearDown(){
        conversationRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void shouldPersistNaturalPersonWithConversation() {
        //given
        prepareNaturalPerson();
        prepareConversation();
        test_naturalPerson.getConversations().add(test_conversation);

        //when
        test_naturalPerson = userRepository.save(test_naturalPerson);

        //then
        assertThat(userRepository.findById(test_naturalPerson.getEmail()).get()).isEqualTo(test_naturalPerson);
        assertThat(userRepository.count()).isEqualTo(1);
        assertThat(conversationRepository.count()).isEqualTo(1);
        assertThat(messageRepository.count()).isEqualTo(1);
    }

    @Test
    public void shouldPersistCompanyWithConversation() {
        //given
        prepareCompany();
        prepareConversation();
        test_company.getConversations().add(test_conversation);

        //when
        test_company = userRepository.save(test_company);

        //then
        assertThat(userRepository.findById(test_company.getEmail()).get()).isEqualTo(test_company);
        assertThat(userRepository.count()).isEqualTo(1);
        assertThat(conversationRepository.count()).isEqualTo(1);
        assertThat(messageRepository.count()).isEqualTo(1);
    }


    @Test
    public void shouldUpdateNaturalPersonWithConversation() {
        //given
        prepareNaturalPerson();
        prepareConversation();
        test_naturalPerson.getConversations().add(test_conversation);
        test_naturalPerson = userRepository.save(test_naturalPerson);

        //when
        test_naturalPerson.setPassword("newtestpassword");
        userRepository.save(test_naturalPerson);

        //then
        assertThat(userRepository.findById(test_naturalPerson.getEmail()).get()).isEqualTo(test_naturalPerson);
        assertThat(userRepository.count()).isEqualTo(1);
        assertThat(conversationRepository.count()).isEqualTo(1);
        assertThat(messageRepository.count()).isEqualTo(1);
    }

    private void prepareMessage() {
        test_message = new Message.Builder()
                .withConversation(test_conversation)
                .withContent(TEST_MESSAGE_CONTENT)
                .build();

        test_messages.add(test_message);
    }

    private void prepareAnnouncement() {
        test_announcement = new Announcement.Builder()
                .withUsers(test_users)
                .build();

        test_announcements.add(test_announcement);
    }

    private void prepareCompany() {
        test_company = new Company.Builder()
                .withEmail(TEST_EMAIL)
                .withPassword(TEST_PASSWORD)
                .withCompanyName(TEST_COMPANY_NAME)
                .withAnnouncements(test_announcements)
                .withConversations(test_conversations)
                .build();
        test_users.add(test_company);
    }

    private void prepareNaturalPerson() {
        test_naturalPerson = new NaturalPerson.Builder()
                .withEmail(TEST_EMAIL)
                .withPassword(TEST_PASSWORD)
                .withFirstName(TEST_FIRST_NAME)
                .withLastName(TEST_LAST_NAME)
                .withAnnouncements(test_announcements)
                .withConversations(test_conversations)
                .build();
        test_users.add(test_naturalPerson);
    }

    private void prepareConversation() {
        test_conversation = new Conversation.Builder()
                .withUsers(test_users)
                .withMessages(test_messages)
                .build();

        prepareMessage();
    }
}
