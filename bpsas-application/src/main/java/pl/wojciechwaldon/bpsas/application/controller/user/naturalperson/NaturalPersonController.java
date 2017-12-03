package pl.wojciechwaldon.bpsas.application.controller.user.naturalperson;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import pl.wojciechwaldon.bpsas.application.exception.user.naturalperson.NaturalPersonNotFoundException;
import pl.wojciechwaldon.bpsas.application.service.user.naturalperson.NaturalPersonService;
import pl.wojciechwaldon.bpsas.domain.model.user.naturalperson.NaturalPerson;

import javax.validation.Valid;

@Controller
@EnableWebMvc
@RequestMapping(value = "/naturalPerson")
public class NaturalPersonController {

    private static Logger logger = LoggerFactory.getLogger(NaturalPersonController.class);

    @Autowired
    private NaturalPersonService naturalPersonService;

    @RequestMapping(value = "/findByEmail", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public NaturalPerson getNaturalPersonByEmail(@RequestParam(value = "email") String email)
            throws NaturalPersonNotFoundException {
        return naturalPersonService.getNaturalPersonByEmail(email);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NaturalPerson> postNaturalPerson(@RequestBody NaturalPerson naturalPerson) {
        return naturalPersonService.postNaturalPerson(naturalPerson);
    }
}
