package pl.wojciechwaldon.bpsas.application.controller.user.company;


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
import pl.wojciechwaldon.bpsas.domain.model.user.company.Company;
import pl.wojciechwaldon.bpsas.domain.repository.announcement.AnnouncementRepository;
import pl.wojciechwaldon.bpsas.domain.repository.conversation.ConversationRepository;
import pl.wojciechwaldon.bpsas.domain.repository.user.company.CompanyRepository;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    private Company company;
    private Message message;
    private Conversation conversation;
    private Announcement announcement;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(companyController).build();
        prepareCompany();
    }

    @After
    public void tearDown() {
        clearDatabase();
    }

    @Test
    public void shouldGetCompanyByEmail() throws Exception {
        mockMvc.perform(get("/company/findByEmail?email=" + company.getEmail()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtils.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("email", is(company.getEmail())))
                .andExpect(jsonPath("password", is(company.getPassword())))
                .andExpect(jsonPath("companyName", is(company.getCompanyName())));
//                .andExpect(jsonPath("conversations.length()", is(company.getConversations().size())))
//                .andExpect(jsonPath("announcements.length()", is(company.getAnnouncements().size())));
    }

    @Test
    public void shouldGetCompanyByCompanyName() throws Exception {
        mockMvc.perform(get("/company/findByCompanyName?companyName=" + company.getCompanyName()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtils.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("email", is(company.getEmail())))
                .andExpect(jsonPath("password", is(company.getPassword())))
                .andExpect(jsonPath("companyName", is(company.getCompanyName())));
//                .andExpect(jsonPath("conversations.length()", is(company.getConversations().size())))
//                .andExpect(jsonPath("announcements.length()", is(company.getAnnouncements().size())));
    }

    private void prepareCompany() {
        company = new Company.Builder()
                .withEmail("test-email")
                .withPassword("test-password")
                .withCompanyName("test-company-name")
                .withAnnouncements(Sets.newSet(announcement))
                .withConversations(Sets.newSet(conversation))
                .build();

        prepareAnnouncement();
        prepareConversation();

        companyRepository.save(company);
    }

    protected void prepareMessage() {
        message = new Message.Builder()
                .withConversation(conversation)
                .withContent("test-message-content")
                .build();
    }

    protected void prepareAnnouncement() {
        announcement = new Announcement.Builder()
                .withUsers(Sets.newSet(company))
                .build();

        announcementRepository.save(announcement);
    }

    protected void prepareConversation() {
        conversation = new Conversation.Builder()
                .withUsers(Sets.newSet(company))
                .withMessages(Arrays.asList(message))
                .build();

        prepareMessage();

        conversationRepository.save(conversation);
    }

    private void clearDatabase() {
        companyRepository.deleteAll();
        conversationRepository.deleteAll();
        announcementRepository.deleteAll();
    }
}
