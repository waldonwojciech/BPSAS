package pl.wojciechwaldon.bpsas.domain.validator.user.company;

import org.apache.commons.lang3.Validate;
import pl.wojciechwaldon.bpsas.domain.model.user.company.Company;

public class CompanyAttributesValidator {

    public static void validate(Company company) {
        Validate.notNull(company.getEmail(), "Email cannot be null.");
        Validate.notNull(company.getPassword(), "Password cannot be null.");
        Validate.notNull(company.getCompanyName(), "Company name cannot be null.");
    }
}
