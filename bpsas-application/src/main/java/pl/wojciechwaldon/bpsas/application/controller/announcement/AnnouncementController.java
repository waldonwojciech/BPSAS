package pl.wojciechwaldon.bpsas.application.controller.announcement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import pl.wojciechwaldon.bpsas.application.service.announcement.AnnouncementService;
import pl.wojciechwaldon.bpsas.domain.model.announcement.Announcement;

import java.util.List;

@Controller
@EnableWebMvc
@RequestMapping(value = "/announcement")
public class AnnouncementController {

    private static Logger logger = LoggerFactory.getLogger(AnnouncementController.class);

    @Autowired
    private AnnouncementService announcementService;

    @RequestMapping(value = "/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Announcement updateUser(@RequestBody Announcement announcement) {
        return announcementService.updateAnnouncement(announcement);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Announcement> findAll() {
        return announcementService.findAll();
    }
}
