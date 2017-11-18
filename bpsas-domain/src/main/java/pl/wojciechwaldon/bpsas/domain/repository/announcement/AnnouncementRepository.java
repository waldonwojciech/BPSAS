package pl.wojciechwaldon.bpsas.domain.repository.announcement;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.wojciechwaldon.bpsas.domain.model.announcement.Announcement;

import java.io.Serializable;

@Repository
public interface AnnouncementRepository extends CrudRepository<Announcement, Long>{
}
