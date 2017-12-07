package pl.wojciechwaldon.bpsas.application.controller.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import pl.wojciechwaldon.bpsas.application.controller.user.naturalperson.NaturalPersonController;
import pl.wojciechwaldon.bpsas.application.exception.user.naturalperson.NaturalPersonNotFoundException;
import pl.wojciechwaldon.bpsas.application.service.user.UserService;
import pl.wojciechwaldon.bpsas.application.service.user.naturalperson.NaturalPersonService;
import pl.wojciechwaldon.bpsas.domain.model.user.User;
import pl.wojciechwaldon.bpsas.domain.model.user.naturalperson.NaturalPerson;

@Controller
@EnableWebMvc
@RequestMapping(value = "/user")
public class UserController {

    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> loginUser(@RequestBody User user) {
        return userService.loginUser(user);
    }
}
