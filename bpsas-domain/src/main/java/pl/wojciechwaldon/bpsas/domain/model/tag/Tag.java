package pl.wojciechwaldon.bpsas.domain.model.tag;

import com.fasterxml.jackson.annotation.JsonBackReference;
import pl.wojciechwaldon.bpsas.domain.model.announcement.Announcement;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
public class Tag implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TAG_GENERATOR")
    @SequenceGenerator(name = "TAG_GENERATOR", sequenceName = "SEQUENCE_TAG", allocationSize = 1)
    private Long id;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
    @JsonBackReference
    private Set<Announcement> announcements;

    Tag() {
    }

    Tag(Builder builder) {
        this.announcements = builder.announcements;
    }

    public Long getId() {
        return id;
    }

    public Set<Announcement> getAnnouncements() {
        return announcements;
    }

    public void setAnnouncements(Set<Announcement> announcements) {
        this.announcements = announcements;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tag tag = (Tag) o;

        if (id != null ? !id.equals(tag.id) : tag.id != null) return false;
        return announcements != null ? announcements.equals(tag.announcements) : tag.announcements == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (announcements != null ? announcements.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                '}';
    }

    public static class Builder {

        private Set<Announcement> announcements;

        public Builder withAnnouncements(Set<Announcement> announcements) {
            this.announcements = announcements;
            return this;
        }

        public Tag build() {
            return new Tag(this);
        }
    }
}
