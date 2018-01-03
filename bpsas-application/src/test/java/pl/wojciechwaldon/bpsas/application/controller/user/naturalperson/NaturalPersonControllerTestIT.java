package pl.wojciechwaldon.bpsas.application.controller.user.naturalperson;

import com.google.gson.Gson;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.util.collections.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pl.wojciechwaldon.bpsas.application.TestUtils;
import pl.wojciechwaldon.bpsas.application.controller.WebRestConfigClassTest;
import pl.wojciechwaldon.bpsas.application.exception.user.naturalperson.NaturalPersonAlreadyExistException;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class NaturalPersonControllerTestIT extends WebRestConfigClassTest {

    private MockMvc mockMvc;

    @Autowired
    private NaturalPersonController naturalPersonController;

    @Autowired
    private NaturalPersonRepository naturalPersonRepository;

    @Autowired
    private ConversationRepository conversationRepository;

    @Autowired
    private AnnouncementRepository announcementRepository;

    private NaturalPerson persistedNaturalPerson;
    private NaturalPerson unpersistedNaturalPerson;
    private Message message;
    private Conversation conversation;
    private Announcement announcement;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(naturalPersonController).build();
        preparePersistedNaturalPerson();
        prepareUnPersistedNaturalPerson();
    }

    @After
    public void tearDown() {
        clearDatabase();
    }

    @Test
    public void shouldGetNaturalPersonByEmail() throws Exception {
        mockMvc.perform(get("/naturalPerson/findByEmail?email=" + persistedNaturalPerson.getEmail()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtils.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("email", is(persistedNaturalPerson.getEmail())))
                .andExpect(jsonPath("password", is(persistedNaturalPerson.getPassword())))
                .andExpect(jsonPath("firstName", is(persistedNaturalPerson.getFirstName())))
                .andExpect(jsonPath("lastName", is(persistedNaturalPerson.getLastName())));
//                .andExpect(jsonPath("conversations.length()", is(unpersistedNaturalPerson.getConversations().size())))
//                .andExpect(jsonPath("announcements.length()", is(unpersistedNaturalPerson.getAnnouncements().size())));
    }

    @Test
    public void shoulThrowNaturalPersonAlreadyExistException() throws Exception {
        Gson gson = new Gson();
        String jsonNaturalPerson = gson.toJson(persistedNaturalPerson, NaturalPerson.class);

        mockMvc.perform(post("/naturalPerson")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonNaturalPerson)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    public void shoulPostNaturalPerson() throws Exception {
       Gson gson = new Gson();
        String jsonNaturalPerson = gson.toJson(unpersistedNaturalPerson, NaturalPerson.class);

        mockMvc.perform(post("/naturalPerson")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonNaturalPerson)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    protected void prepareMessage() {
        message = new Message.Builder()
                .withConversation(conversation)
                .withContent("test-message-content")
                .build();
    }

    protected void prepareAnnouncement() {
        announcement = new Announcement.Builder()
                .withUser(persistedNaturalPerson)
                .build();

        announcementRepository.save(announcement);
    }

    protected void prepareConversation() {
        conversation = new Conversation.Builder()
                .withUsers(Sets.newSet(persistedNaturalPerson))
                .withMessages(Arrays.asList(message))
                .build();

        prepareMessage();

        conversationRepository.save(conversation);
    }

    private void preparePersistedNaturalPerson() {
        persistedNaturalPerson = new NaturalPerson.Builder()
                .withEmail("test-email")
                .withPassword("test-password")
                .withFirstName("test-first-name")
                .withLastName("test-last-name")
                .withAnnouncements(Sets.newSet(announcement))
                .withConversations(Sets.newSet(conversation))
                .build();

        naturalPersonRepository.save(persistedNaturalPerson);
        prepareAnnouncement();
        prepareConversation();


    }

    private void prepareUnPersistedNaturalPerson() {
        unpersistedNaturalPerson = new NaturalPerson.Builder()
                .withEmail("test-email-unpresisted")
                .withPassword("test-password-unpresisted")
                .withFirstName("test-first-name-unpresisted")
                .withLastName("test-last-name-unpresisted")
                .build();

        prepareAnnouncement();
        prepareConversation();
    }

    private void clearDatabase() {
        conversationRepository.deleteAll();
        announcementRepository.deleteAll();
        naturalPersonRepository.deleteAll();
    }
}
