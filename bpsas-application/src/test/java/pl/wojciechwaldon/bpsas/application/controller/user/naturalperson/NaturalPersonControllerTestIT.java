package pl.wojciechwaldon.bpsas.application.controller.user.naturalperson;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.util.collections.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.wojciechwaldon.bpsas.application.TestUtils;
import pl.wojciechwaldon.bpsas.application.controller.WebRestConfigClassTest;
import pl.wojciechwaldon.bpsas.domain.model.announcement.Announcement;
import pl.wojciechwaldon.bpsas.domain.model.conversation.Conversation;
import pl.wojciechwaldon.bpsas.domain.model.message.Message;
import pl.wojciechwaldon.bpsas.domain.model.user.naturalperson.NaturalPerson;
import pl.wojciechwaldon.bpsas.domain.repository.announcement.AnnouncementRepository;
import pl.wojciechwaldon.bpsas.domain.repository.conversation.ConversationRepository;
import pl.wojciechwaldon.bpsas.domain.repository.user.naturalperson.NaturalPersonRepository;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
public class NaturalPersonControllerTestIT extends WebRestConfigClassTest{

    private MockMvc mockMvc;

    @Autowired
    private NaturalPersonController naturalPersonController;

    @Autowired
    private NaturalPersonRepository naturalPersonRepository;

    @Autowired
    private ConversationRepository conversationRepository;

    @Autowired
    private AnnouncementRepository announcementRepository;

    private NaturalPerson naturalPerson;
    private Message message;
    private Conversation conversation;
    private Announcement announcement;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(naturalPersonController).build();
        prepareNaturalPerson();
    }

    @After
    public void tearDown() {
        clearDatabase();
    }

    @Test
    public void shouldGetCompanyByEmail() throws Exception {
        mockMvc.perform(get("/naturalPerson/findByEmail?email=" + naturalPerson.getEmail()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtils.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("email", is(naturalPerson.getEmail())))
                .andExpect(jsonPath("password", is(naturalPerson.getPassword())))
                .andExpect(jsonPath("firstName", is(naturalPerson.getFirstName())))
                .andExpect(jsonPath("lastName", is(naturalPerson.getLastName())))
                .andExpect(jsonPath("conversations.length()", is(naturalPerson.getConversations().size())))
                .andExpect(jsonPath("announcements.length()", is(naturalPerson.getAnnouncements().size())));
    }

    protected void prepareMessage() {
        message = new Message.Builder()
                .withConversation(conversation)
                .withContent("test-message-content")
                .build();
    }

    protected void prepareAnnouncement() {
        announcement = new Announcement.Builder()
                .withUsers(Sets.newSet(naturalPerson))
                .build();

        announcementRepository.save(announcement);
    }

    protected void prepareConversation() {
        conversation = new Conversation.Builder()
                .withUsers(Sets.newSet(naturalPerson))
                .withMessages(Arrays.asList(message))
                .build();

        prepareMessage();

        conversationRepository.save(conversation);
    }

    private void prepareNaturalPerson() {
        naturalPerson = new NaturalPerson.Builder()
                .withEmail("test-email")
                .withPassword("test-password")
                .withFirstName("test-first-name")
                .withLastName("test-last-name")
                .withAnnouncements(Sets.newSet(announcement))
                .withConversations(Sets.newSet(conversation))
                .build();

        prepareAnnouncement();
        prepareConversation();

        naturalPersonRepository.save(naturalPerson);

    }

    private void clearDatabase() {
        naturalPersonRepository.deleteAll();
        conversationRepository.deleteAll();
        announcementRepository.deleteAll();
    }
}
