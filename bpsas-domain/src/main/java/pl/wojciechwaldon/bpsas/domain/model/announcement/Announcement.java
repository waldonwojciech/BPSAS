package pl.wojciechwaldon.bpsas.domain.model.announcement;

import pl.wojciechwaldon.bpsas.domain.model.tag.Tag;
import pl.wojciechwaldon.bpsas.domain.model.user.User;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Announcement {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ANNOUNCEMENT_GENERATOR")
    @SequenceGenerator(name = "ANNOUNCEMENT_GENERATOR", sequenceName = "SEQUENCE_ANNOUNCEMENT", allocationSize = 1)
    private Long id;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
    private Set<User> users;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE}, mappedBy = "announcements")
    private Set<Tag> tags;

    public Announcement() {
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

        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Announcement{" +
                "id=" + id +
                ", users=" + users.size() +
                '}';
    }
}
