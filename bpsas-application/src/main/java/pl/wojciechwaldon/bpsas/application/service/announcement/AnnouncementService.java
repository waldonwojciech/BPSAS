package pl.wojciechwaldon.bpsas.application.service.announcement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.wojciechwaldon.bpsas.domain.model.announcement.Announcement;
import pl.wojciechwaldon.bpsas.domain.model.tag.Tag;
import pl.wojciechwaldon.bpsas.domain.repository.announcement.AnnouncementRepository;
import pl.wojciechwaldon.bpsas.domain.repository.tag.TagRepository;

import java.util.HashSet;
import java.util.List;

@Service
public class AnnouncementService {

    @Autowired
    private AnnouncementRepository announcementRepository;

    @Autowired
    private TagRepository tagRepository;


    public Announcement updateAnnouncement(Announcement announcement) {
        Announcement savedAnnouncement = announcementRepository.save(announcement);

        for (Tag tag : announcement.getTags()) {
            if (tag.getAnnouncements() == null)
                tag.setAnnouncements(new HashSet<>());
            tag.getAnnouncements().add(savedAnnouncement);
            tagRepository.save(tag);
        }

        return savedAnnouncement;
    }

    public List<Announcement> findAll() {
        return (List<Announcement>) announcementRepository.findAll();
    }
}
