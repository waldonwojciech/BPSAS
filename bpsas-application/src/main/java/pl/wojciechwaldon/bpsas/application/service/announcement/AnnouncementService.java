package pl.wojciechwaldon.bpsas.application.service.announcement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.wojciechwaldon.bpsas.domain.model.announcement.Announcement;
import pl.wojciechwaldon.bpsas.domain.model.tag.Tag;
import pl.wojciechwaldon.bpsas.domain.model.user.User;
import pl.wojciechwaldon.bpsas.domain.repository.announcement.AnnouncementRepository;
import pl.wojciechwaldon.bpsas.domain.repository.tag.TagRepository;
import pl.wojciechwaldon.bpsas.domain.repository.user.UserRepository;

import java.util.*;

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

        Tag[] tags = new Tag[announcement.getTags().size()];
        announcement.getTags().toArray(tags);
        for (Tag tag : tags) {
            Optional<Tag> persistedTag = tagRepository.findByName(tag.getName());
            if(persistedTag.isPresent()) {
                persistedTag.get().getAnnouncements().add(announcement);

                tagRepository.save(persistedTag.get());
            }else {
                if (tag.getAnnouncements() == null)
                    tag.setAnnouncements(new ArrayList<>());
                tag.getAnnouncements().add(announcement);
                tagRepository.save(tag);
            }
        }

        return savedAnnouncement;
    }

    public List<Announcement> findAll() {
        return (List<Announcement>) announcementRepository.findAll();
    }
}
