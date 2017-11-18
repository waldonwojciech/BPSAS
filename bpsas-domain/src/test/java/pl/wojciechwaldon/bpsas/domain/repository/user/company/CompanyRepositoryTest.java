package pl.wojciechwaldon.bpsas.domain.repository.user.company;

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
import pl.wojciechwaldon.bpsas.domain.model.user.User;
import pl.wojciechwaldon.bpsas.domain.model.user.company.Company;
import pl.wojciechwaldon.bpsas.domain.repository.BaseRepositoryTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestSpringBootApplicationClass.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class})
@WebAppConfiguration
public class CompanyRepositoryTest extends BaseRepositoryTest {

    @Autowired
    private CompanyRepository companyRepository;

    private final String TEST_NEW_PASSWORD = "test-new-password";
    private final String TEST_COMPANY_NAME = "test-company-name";

    private Company test_company;

    @After
    public void tearDown() {
        conversationRepository.deleteAll();
        companyRepository.deleteAll();
    }

    @Test
    public void shouldPersistCompanyWithConversation() {
        //given
        prepareCompany();
        prepareConversation();
        test_company.getConversations().add(test_conversation);

        //when
        test_company = companyRepository.save(test_company);

        User fetchedCompany = companyRepository.findById(test_company.getEmail()).get();

        //then
        assertThat(fetchedCompany).isEqualTo(test_company);
        assertThat(companyRepository.count()).isEqualTo(1);
        assertThat(conversationRepository.count()).isEqualTo(1);
        assertThat(messageRepository.count()).isEqualTo(1);
    }


    @Test
    public void shouldUpdateCompanyWithConversation() {
        //given
        prepareCompany();
        prepareConversation();
        test_company.getConversations().add(test_conversation);
        test_company = companyRepository.save(test_company);

        //when
        test_company.setPassword(TEST_NEW_PASSWORD);
        companyRepository.save(test_company);

        User fetchedCompany = companyRepository.findById(test_company.getEmail()).get();

        //then
        assertThat(fetchedCompany).isEqualTo(test_company);
        assertThat(companyRepository.count()).isEqualTo(1);
        assertThat(conversationRepository.count()).isEqualTo(1);
        assertThat(messageRepository.count()).isEqualTo(1);
    }

    @Test
    public void shouldPersistCompanyWithAnnoucement() {
        //given
        prepareCompany();
        prepareAnnouncement();

        //when
        test_company.getAnnouncements().add(test_announcement);
        test_company = companyRepository.save(test_company);

        User fetchedCompany = companyRepository.findById(test_company.getEmail()).get();

        //then
        assertThat(fetchedCompany).isEqualTo(test_company);
        assertThat(companyRepository.count()).isEqualTo(1);
        assertThat(announcementRepository.count()).isEqualTo(1);
    }


    @Test
    public void shouldUpdateCompanyWithAnnoucement() {
        //given
        prepareCompany();
        prepareAnnouncement();
        test_company.getAnnouncements().add(test_announcement);
        test_company = companyRepository.save(test_company);

        //when
        test_company.setPassword(TEST_NEW_PASSWORD);
        test_company = companyRepository.save(test_company);

        User fetchedCompany = companyRepository.findById(test_company.getEmail()).get();

        //then
        assertThat(fetchedCompany).isEqualTo(test_company);
        assertThat(companyRepository.count()).isEqualTo(1);
        assertThat(announcementRepository.count()).isEqualTo(1);
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
}
