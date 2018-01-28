package pl.wojciechwaldon.bpsas.domain.model.tag;

import com.fasterxml.jackson.annotation.*;
import pl.wojciechwaldon.bpsas.domain.model.announcement.Announcement;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
public class Tag implements Serializable {

    @Id
    @NotNull
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Announcement> announcements;

    Tag() {
    }

    Tag(Builder builder) {
        this.announcements = builder.announcements;
        this.name = builder.name;
    }

    public String getName() {
        return name;
    }

    public List<Announcement> getAnnouncements() {
        return announcements;
    }

    public void setAnnouncements(List<Announcement> announcements) {
        this.announcements = announcements;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag = (Tag) o;
        return Objects.equals(name, tag.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Tag{" +
                "name='" + name + '\'' +
                '}';
    }

    public static class Builder {

        private List<Announcement> announcements;

        private String name;

        public Builder withAnnouncements(List<Announcement> announcements) {
            this.announcements = announcements;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Tag build() {
            return new Tag(this);
        }
    }
}
