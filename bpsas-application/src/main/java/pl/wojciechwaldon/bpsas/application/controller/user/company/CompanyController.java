package pl.wojciechwaldon.bpsas.application.controller.user.company;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.wojciechwaldon.bpsas.application.exception.user.company.CompanyNotFoundException;
import pl.wojciechwaldon.bpsas.application.service.user.company.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import pl.wojciechwaldon.bpsas.domain.model.user.company.Company;

@Controller
@RequestMapping(value = "/company")
public class CompanyController {

    private static Logger logger = LoggerFactory.getLogger(CompanyController.class);

    @Autowired
    private CompanyService companyService;

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Company> postCompany(@RequestBody Company company) {
        return companyService.postCompany(company);
    }

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
