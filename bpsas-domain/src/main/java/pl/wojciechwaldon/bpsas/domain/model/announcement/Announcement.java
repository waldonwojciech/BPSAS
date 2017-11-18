package pl.wojciechwaldon.bpsas.domain.model.announcement;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import pl.wojciechwaldon.bpsas.domain.model.tag.Tag;
import pl.wojciechwaldon.bpsas.domain.model.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
public class Announcement {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ANNOUNCEMENT_GENERATOR")
    @SequenceGenerator(name = "ANNOUNCEMENT_GENERATOR", sequenceName = "SEQUENCE_ANNOUNCEMENT", allocationSize = 1)
    private Long id;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
    @NotNull
    @JsonBackReference
    private Set<User> users;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE}, mappedBy = "announcements")
    @JsonManagedReference
    private Set<Tag> tags;

    Announcement() {
    }

    Announcement(Builder builder) {
        this.users = builder.users;
        this.tags = builder.tags;
    }

    public Long getId() {
        return id;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Announcement that = (Announcement) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (users != null ? !users.equals(that.users) : that.users != null) return false;
        return tags != null ? tags.equals(that.tags) : that.tags == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (users != null ? users.hashCode() : 0);
        result = 31 * result + (tags != null ? tags.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Announcement{" +
                "id=" + id +
                ", tags=" + tags +
                '}';
    }

    public static class Builder {

        private Set<User> users;
        private Set<Tag> tags;

        public Builder withUsers(@NotNull Set<User> users) {
            this.users = users;
            return  this;
        }

        public Builder withTags(Set<Tag> tags) {
            this.tags = tags;
            return  this;
        }

        public Announcement build() {
            return new Announcement(this);
        }
    }
}
