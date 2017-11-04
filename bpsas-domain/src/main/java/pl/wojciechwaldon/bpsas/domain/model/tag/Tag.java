package pl.wojciechwaldon.bpsas.domain.model.tag;

import pl.wojciechwaldon.bpsas.domain.model.announcement.Announcement;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
public class Tag implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TAG_GENERATOR")
    @SequenceGenerator(name = "TAG_GENERATOR", sequenceName = "SEQUENCE_TAG", allocationSize = 1)
    private Long id;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
    private Set<Announcement> announcements;

    public Tag() {
    }


}
