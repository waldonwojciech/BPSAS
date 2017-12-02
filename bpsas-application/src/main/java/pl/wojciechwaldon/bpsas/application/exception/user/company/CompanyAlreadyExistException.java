package pl.wojciechwaldon.bpsas.application.exception.user.company;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.FORBIDDEN, reason="Company already exist.")
public class CompanyAlreadyExistException extends RuntimeException{
}
