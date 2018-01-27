package pl.wojciechwaldon.bpsas.application.service.announcement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.wojciechwaldon.bpsas.domain.model.announcement.Announcement;
import pl.wojciechwaldon.bpsas.domain.model.tag.Tag;
import pl.wojciechwaldon.bpsas.domain.model.user.User;
import pl.wojciechwaldon.bpsas.domain.repository.announcement.AnnouncementRepository;
import pl.wojciechwaldon.bpsas.domain.repository.tag.TagRepository;
import pl.wojciechwaldon.bpsas.domain.repository.user.UserRepository;

import java.util.HashSet;
import java.util.List;

@Service
public class AnnouncementService {

    @Autowired
    private AnnouncementRepository announcementRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TagRepository tagRepository;


    public Announcement updateAnnouncement(Announcement announcement) {
        Announcement savedAnnouncement = announcementRepository.save(announcement);
        User user = userRepository.findByEmail(announcement.getUser().getEmail()).get();

        user.getAnnouncements().add(announcement);

        for (Tag tag : announcement.getTags()) {
            if (tag.getAnnouncements() == null)
                tag.setAnnouncements(new HashSet<>());
            tag.getAnnouncements().add(savedAnnouncement);
            tagRepository.save(tag);
        }

        userRepository.save(user);

        return savedAnnouncement;
    }

    public List<Announcement> findAll() {
        return (List<Announcement>) announcementRepository.findAll();
    }
}
