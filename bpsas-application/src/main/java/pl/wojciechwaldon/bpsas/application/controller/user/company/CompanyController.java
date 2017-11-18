package pl.wojciechwaldon.bpsas.application.controller.user.company;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.wojciechwaldon.bpsas.application.exception.user.company.CompanyNotFoundException;
import pl.wojciechwaldon.bpsas.application.service.user.company.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.wojciechwaldon.bpsas.domain.model.user.company.Company;

@Controller
@RequestMapping(value = "/company")
public class CompanyController {

    private static Logger logger = LoggerFactory.getLogger(CompanyController.class);

    @Autowired
    private CompanyService companyService;

    @RequestMapping(value = "/findByCompanyName", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Company getCompanyByCompanyName(@RequestParam(value = "companyName", required = false) String companyName)
    throws CompanyNotFoundException {
        return companyService.getCompanyByCompanyName(companyName);
    }

    @RequestMapping(value = "/findByEmail", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Company getCompanyByEmail(@RequestParam(value = "email", required = false) String email)
            throws CompanyNotFoundException{
        return companyService.getCompanyByEmail(email);
    }
}
