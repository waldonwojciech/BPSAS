package pl.wojciechwaldon.bpsas.domain.validator;

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
import pl.wojciechwaldon.bpsas.domain.model.user.naturalperson.NaturalPerson;
import pl.wojciechwaldon.bpsas.domain.repository.user.UserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestSpringBootApplicationClass.class })
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class })
@WebAppConfiguration
public class AttributesValidatorTest {

    @Autowired
    private UserRepository userRepository;

    @Test(expected = NullPointerException.class)
    public void shouldThrowAttributeNullPointerException() {
        //given
        NaturalPerson invalidNaturalPerson = new NaturalPerson.Builder()
                .withEmail("test_email")
                .withFirstName("test_first_name")
                .withLastName("test_last_name")
                .build();

        //when
        invalidNaturalPerson = userRepository.save(invalidNaturalPerson);
    }
}
