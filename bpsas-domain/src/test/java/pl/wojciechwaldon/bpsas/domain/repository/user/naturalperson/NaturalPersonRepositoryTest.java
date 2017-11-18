package pl.wojciechwaldon.bpsas.domain.repository.user.naturalperson;

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
import pl.wojciechwaldon.bpsas.domain.repository.conversation.ConversationRepository;
import pl.wojciechwaldon.bpsas.domain.repository.message.MessageRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestSpringBootApplicationClass.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class})
@WebAppConfiguration
public class NaturalPersonRepositoryTest extends BaseRepositoryTest{

    @Autowired
    private NaturalPersonRepository naturalPersonRepository;

    private final String TEST_NEW_PASSWORD = "test-new-password";
    private final String TEST_FIRST_NAME = "test-first-name";
    private final String TEST_LAST_NAME = "test-last-name";

    private NaturalPerson test_naturalPerson;

    @After
    public void tearDown() {
        conversationRepository.deleteAll();
        naturalPersonRepository.deleteAll();
    }

    @Test
    public void shouldPersistNaturalPersonWithConversation() {
        //given
        prepareNaturalPerson();
        prepareConversation();
        test_naturalPerson.getConversations().add(test_conversation);

        //when
        test_naturalPerson = naturalPersonRepository.save(test_naturalPerson);

        NaturalPerson fetchedNaturalPerson = naturalPersonRepository.findById(test_naturalPerson.getEmail()).get();

        //then
        assertThat(fetchedNaturalPerson).isEqualTo(test_naturalPerson);
        assertThat(naturalPersonRepository.count()).isEqualTo(1);
        assertThat(conversationRepository.count()).isEqualTo(1);
        assertThat(messageRepository.count()).isEqualTo(1);
    }

    @Test
    public void shouldUpdateNaturalPersonWithConversation() {
        //given
        prepareNaturalPerson();
        prepareConversation();
        test_naturalPerson.getConversations().add(test_conversation);
        test_naturalPerson = naturalPersonRepository.save(test_naturalPerson);

        //when
        test_naturalPerson.setPassword(TEST_NEW_PASSWORD);
        naturalPersonRepository.save(test_naturalPerson);

        User fetchedNaturalPerson = naturalPersonRepository.findById(test_naturalPerson.getEmail()).get();

        //then
        assertThat(fetchedNaturalPerson).isEqualTo(test_naturalPerson);
        assertThat(naturalPersonRepository.count()).isEqualTo(1);
        assertThat(conversationRepository.count()).isEqualTo(1);
        assertThat(messageRepository.count()).isEqualTo(1);
    }

    @Test
    public void shouldPersistNaturalPersonWithAnnoucement() {
        //given
        prepareNaturalPerson();
        prepareAnnouncement();

        //when
        test_naturalPerson.getAnnouncements().add(test_announcement);
        test_naturalPerson = naturalPersonRepository.save(test_naturalPerson);

        User fetchedNaturalPerson = naturalPersonRepository.findById(test_naturalPerson.getEmail()).get();

        //then
        assertThat(fetchedNaturalPerson).isEqualTo(test_naturalPerson);
        assertThat(naturalPersonRepository.count()).isEqualTo(1);
        assertThat(announcementRepository.count()).isEqualTo(1);
    }

    @Test
    public void shouldUpdateNaturalPersonWithAnnoucement() {
        //given
        prepareNaturalPerson();
        prepareAnnouncement();
        test_naturalPerson.getAnnouncements().add(test_announcement);
        test_naturalPerson = naturalPersonRepository.save(test_naturalPerson);

        //when
        test_naturalPerson.setPassword(TEST_NEW_PASSWORD);
        test_naturalPerson = naturalPersonRepository.save(test_naturalPerson);

        User fetchedNaturalPerson = naturalPersonRepository.findById(test_naturalPerson.getEmail()).get();

        //then
        assertThat(fetchedNaturalPerson).isEqualTo(test_naturalPerson);
        assertThat(naturalPersonRepository.count()).isEqualTo(1);
        assertThat(announcementRepository.count()).isEqualTo(1);
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
}
