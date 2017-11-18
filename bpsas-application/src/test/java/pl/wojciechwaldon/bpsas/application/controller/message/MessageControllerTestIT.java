package pl.wojciechwaldon.bpsas.application.controller.message;

import com.google.common.primitives.Ints;
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
import pl.wojciechwaldon.bpsas.domain.repository.message.MessageRepository;
import pl.wojciechwaldon.bpsas.domain.repository.user.company.CompanyRepository;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class MessageControllerTestIT extends WebRestConfigClassTest {

    private MockMvc mockMvc;

    @Autowired
    private MessageController messageController;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private MessageRepository messageRepository;

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
        mockMvc = MockMvcBuilders.standaloneSetup(messageController).build();
        prepareCompany();
    }

    @After
    public void tearDown() {
        clearDatabase();
    }

    @Test
    public void shouldGetByConversationId() throws Exception {
        mockMvc.perform(get("/message/findByConversationId?conversationId=" + message.getConversation().getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtils.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("[0].id", is(Ints.checkedCast(message.getId()))))
                .andExpect(jsonPath("[0].content", is(message.getContent())))
                .andExpect(jsonPath("[0].conversation.id", is(Ints.checkedCast(message.getConversation().getId()))));
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

        company = companyRepository.save(company);
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

        announcement = announcementRepository.save(announcement);
    }

    protected void prepareConversation() {
        conversation = new Conversation.Builder()
                .withUsers(Sets.newSet(company))
                .withMessages(Arrays.asList(message))
                .build();

        prepareMessage();
        conversation = conversationRepository.save(conversation);
        message = messageRepository.save(message);
    }

    private void clearDatabase() {
        companyRepository.deleteAll();
        conversationRepository.deleteAll();
        messageRepository.deleteAll();
        announcementRepository.deleteAll();
    }
}
