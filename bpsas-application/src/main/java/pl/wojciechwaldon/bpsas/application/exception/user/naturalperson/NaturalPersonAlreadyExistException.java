package pl.wojciechwaldon.bpsas.application.exception.user.naturalperson;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.FORBIDDEN, reason="Natural person already exist.")
public class NaturalPersonAlreadyExistException extends RuntimeException{
}
