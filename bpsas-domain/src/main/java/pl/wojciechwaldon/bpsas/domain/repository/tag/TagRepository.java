package pl.wojciechwaldon.bpsas.domain.repository.tag;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.wojciechwaldon.bpsas.domain.model.tag.Tag;

@Repository
public interface TagRepository extends CrudRepository<Tag, Long> {
}
