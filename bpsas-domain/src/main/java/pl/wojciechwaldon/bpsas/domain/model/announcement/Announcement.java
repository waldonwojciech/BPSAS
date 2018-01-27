package pl.wojciechwaldon.bpsas.domain.model.announcement;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import pl.wojciechwaldon.bpsas.domain.model.tag.Tag;
import pl.wojciechwaldon.bpsas.domain.model.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

@Entity
public class Announcement {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ANNOUNCEMENT_GENERATOR")
    @SequenceGenerator(name = "ANNOUNCEMENT_GENERATOR", sequenceName = "SEQUENCE_ANNOUNCEMENT", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
    @NotNull
    @JsonProperty
    private User user;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.REFRESH}, mappedBy = "announcements")
    @JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
    private Set<Tag> tags;

    @JsonProperty
    private String content;

    @JsonProperty
    private String title;

    @JsonProperty
    private Date date;

    public Announcement() {
    }

    Announcement(Builder builder) {
        this.user = builder.user;
        this.tags = builder.tags;
        this.content = builder.content;
        this.title = builder.title;
        this.date = builder.date;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public String getContent() {
        return content;
    }

    public String getTitle() {
        return title;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Announcement that = (Announcement) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Announcement{" +
                "id=" + id +
                '}';
    }

    public static class Builder {

        private User user;
        private Set<Tag> tags;
        private String content;
        private String title;
        private Date date;

        public Builder withUser(@NotNull User user) {
            this.user = user;
            return this;
        }

        public Builder withTags(Set<Tag> tags) {
            this.tags = tags;
            return this;
        }

        public Builder withContent(String content) {
            this.content = content;
            return this;
        }

        public Builder withTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder withDate(Date date) {
            this.date = date;
            return this;
        }

        public Announcement build() {
            return new Announcement(this);
        }
    }
}
