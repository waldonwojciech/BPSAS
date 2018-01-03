package pl.wojciechwaldon.bpsas.application.controller.tag;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import pl.wojciechwaldon.bpsas.application.service.tag.TagService;
import pl.wojciechwaldon.bpsas.domain.model.tag.Tag;

import java.util.List;

@Controller
@EnableWebMvc
@RequestMapping(value = "/tag")
public class TagController {

    private static Logger logger = LoggerFactory.getLogger(TagController.class);

    @Autowired
    private TagService tagService;

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Tag> findAll() {
        return tagService.findAll();
    }
}
