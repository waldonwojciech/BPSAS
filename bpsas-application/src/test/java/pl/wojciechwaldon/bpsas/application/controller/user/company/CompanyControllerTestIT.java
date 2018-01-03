package pl.wojciechwaldon.bpsas.application.controller.user.company;


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
import pl.wojciechwaldon.bpsas.domain.model.announcement.Announcement;
import pl.wojciechwaldon.bpsas.domain.model.conversation.Conversation;
import pl.wojciechwaldon.bpsas.domain.model.message.Message;
import pl.wojciechwaldon.bpsas.domain.model.user.company.Company;
import pl.wojciechwaldon.bpsas.domain.repository.announcement.AnnouncementRepository;
import pl.wojciechwaldon.bpsas.domain.repository.conversation.ConversationRepository;
import pl.wojciechwaldon.bpsas.domain.repository.user.company.CompanyRepository;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class CompanyControllerTestIT extends WebRestConfigClassTest {

    private MockMvc mockMvc;

    @Autowired
    private CompanyController companyController;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private ConversationRepository conversationRepository;

    @Autowired
    private AnnouncementRepository announcementRepository;

    private Company persistedCompany;
    private Company unpersistedCompany;
    private Message message;
    private Conversation conversation;
    private Announcement announcement;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(companyController).build();
        preparePersistedCompany();
        prepareUnersistedCompany();
    }

    @After
    public void tearDown() {
        clearDatabase();
    }


    @Test
    public void shoulThrowCompanyAlreadyExistException() throws Exception {
        Gson gson = new Gson();
        String jsonCompany = gson.toJson(persistedCompany, Company.class);

        mockMvc.perform(post("/company")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonCompany)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    public void shoulPostCompany() throws Exception {
        Gson gson = new Gson();
        String jsonCompany = gson.toJson(unpersistedCompany, Company.class);

        mockMvc.perform(post("/company")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonCompany)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldGetCompanyByEmail() throws Exception {
        mockMvc.perform(get("/persistedCompany/findByEmail?email=" + persistedCompany.getEmail()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtils.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("email", is(persistedCompany.getEmail())))
                .andExpect(jsonPath("password", is(persistedCompany.getPassword())))
                .andExpect(jsonPath("companyName", is(persistedCompany.getCompanyName())));
//                .andExpect(jsonPath("conversations.length()", is(persistedCompany.getConversations().size())))
//                .andExpect(jsonPath("announcements.length()", is(persistedCompany.getAnnouncements().size())));
    }

    @Test
    public void shouldGetCompanyByCompanyName() throws Exception {
        mockMvc.perform(get("/persistedCompany/findByCompanyName?companyName=" + persistedCompany.getCompanyName()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtils.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("email", is(persistedCompany.getEmail())))
                .andExpect(jsonPath("password", is(persistedCompany.getPassword())))
                .andExpect(jsonPath("companyName", is(persistedCompany.getCompanyName())));
//                .andExpect(jsonPath("conversations.length()", is(persistedCompany.getConversations().size())))
//                .andExpect(jsonPath("announcements.length()", is(persistedCompany.getAnnouncements().size())));
    }

    private void preparePersistedCompany() {
        persistedCompany = new Company.Builder()
                .withEmail("test-email-persisted")
                .withPassword("test-password")
                .withCompanyName("test-persistedCompany-name")
                .withAnnouncements(Sets.newSet(announcement))
                .withConversations(Sets.newSet(conversation))
                .build();

        companyRepository.save(persistedCompany);
        prepareAnnouncement();
        prepareConversation();
    }

    private void prepareUnersistedCompany() {
        unpersistedCompany = new Company.Builder()
                .withEmail("test-email-unpersisted")
                .withPassword("test-password")
                .withCompanyName("test-persistedCompany-name")
                .withAnnouncements(Sets.newSet(announcement))
                .withConversations(Sets.newSet(conversation))
                .build();

        prepareAnnouncement();
        companyRepository.save(unpersistedCompany);
        prepareConversation();
    }

    protected void prepareMessage() {
        message = new Message.Builder()
                .withConversation(conversation)
                .withContent("test-message-content")
                .build();
    }

    protected void prepareAnnouncement() {
        announcement = new Announcement.Builder()
                .withUser(persistedCompany)
                .build();

        announcementRepository.save(announcement);
    }

    protected void prepareConversation() {
        conversation = new Conversation.Builder()
                .withUsers(Sets.newSet(persistedCompany))
                .withMessages(Arrays.asList(message))
                .build();

        prepareMessage();

        conversationRepository.save(conversation);
    }

    private void clearDatabase() {
        conversationRepository.deleteAll();
        announcementRepository.deleteAll();
        companyRepository.deleteAll();
    }
}
