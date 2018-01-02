package pl.wojciechwaldon.bpsas.application.service.announcement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.wojciechwaldon.bpsas.domain.model.announcement.Announcement;
import pl.wojciechwaldon.bpsas.domain.repository.announcement.AnnouncementRepository;

@Service
public class AnnouncementService {

    @Autowired
    private AnnouncementRepository announcementRepository;


    public Announcement updateAnnouncement(Announcement announcement) {
        return announcementRepository.save(announcement);
    }
}
