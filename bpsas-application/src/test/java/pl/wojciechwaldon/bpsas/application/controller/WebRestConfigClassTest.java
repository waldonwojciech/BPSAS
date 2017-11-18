package pl.wojciechwaldon.bpsas.application.controller;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import pl.wojciechwaldon.bpsas.application.BusinessPartnerSearchAssistanceSystemApplication;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;

@ContextConfiguration(classes = {BusinessPartnerSearchAssistanceSystemApplication.class })
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, TransactionalTestExecutionListener.class })
@WebAppConfiguration
public class WebRestConfigClassTest {

}