package pl.wojciechwaldon.bpsas.domain.validator.user.naturalperson;

import org.apache.commons.lang3.Validate;
import pl.wojciechwaldon.bpsas.domain.model.user.naturalperson.NaturalPerson;

public class NaturalPersonAttributesValidator {

    public static void validate(NaturalPerson naturalPerson) {
        Validate.notNull(naturalPerson.getEmail(), "Email cannot be null.");
        Validate.notNull(naturalPerson.getPassword(), "Password cannot be null.");
        Validate.notNull(naturalPerson.getFirstName(), "First name cannot be null..");
        Validate.notNull(naturalPerson.getLastName(), "Last name cannot be null.");
    }
}
