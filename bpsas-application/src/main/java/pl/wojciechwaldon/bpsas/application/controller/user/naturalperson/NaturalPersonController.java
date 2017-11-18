package pl.wojciechwaldon.bpsas.application.controller.user.naturalperson;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.wojciechwaldon.bpsas.application.exception.user.naturalperson.NaturalPersonNotFoundException;
import pl.wojciechwaldon.bpsas.application.service.user.naturalperson.NaturalPersonService;
import pl.wojciechwaldon.bpsas.domain.model.user.naturalperson.NaturalPerson;

@Controller
@RequestMapping(value = "/naturalPerson")
public class NaturalPersonController {

    private static Logger logger = LoggerFactory.getLogger(NaturalPersonController.class);

    @Autowired
    private NaturalPersonService naturalPersonService;

    @RequestMapping(value = "/findByEmail", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public NaturalPerson getNaturalPersonByEmail(@RequestParam(value = "email") String email)
            throws NaturalPersonNotFoundException {
        return naturalPersonService.getNaturalPersonByEmail(email);
    }
}
