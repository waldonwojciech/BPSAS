package pl.wojciechwaldon.bpsas.application.exception.tag;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="Tag not found.")
public class TagNotFoundException extends RuntimeException{
}
