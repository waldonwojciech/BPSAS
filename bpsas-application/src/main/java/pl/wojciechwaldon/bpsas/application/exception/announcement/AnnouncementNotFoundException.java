package pl.wojciechwaldon.bpsas.application.exception.announcement;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="Announcement not found.")
public class AnnouncementNotFoundException extends RuntimeException{
}
