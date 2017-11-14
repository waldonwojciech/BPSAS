package pl.wojciechwaldon.bpsas.domain.validator.announcement;

import org.apache.commons.lang3.Validate;
import pl.wojciechwaldon.bpsas.domain.model.announcement.Announcement;

public class AnnouncementAttributesValidator {

    public static void validate(Announcement announcement) {
        Validate.notNull(announcement.getUsers(), "Users cannot be null.");
    }
}
