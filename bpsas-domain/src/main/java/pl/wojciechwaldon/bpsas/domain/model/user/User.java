package pl.wojciechwaldon.bpsas.domain.model.user;

import pl.wojciechwaldon.bpsas.domain.model.announcement.Announcement;
import pl.wojciechwaldon.bpsas.domain.model.conversation.Conversation;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

@Entity
@Inheritance( strategy = InheritanceType.TABLE_PER_CLASS)
public class User implements Serializable{

    @Id
    @NotNull
    protected String email;

    @NotNull
    protected String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REMOVE}, mappedBy = "users")
    protected Set<Conversation> conversations;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REMOVE}, mappedBy = "users")
    protected Set<Announcement> announcements;

    protected User() {
    }

    protected User(String email, @NotNull String password, Set<Conversation> conversations, Set<Announcement> announcements) {
        this.email = email;
        this.password = password;
        this.conversations = conversations;
        this.announcements = announcements;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Conversation> getConversations() {
        return conversations;
    }

    public void setConversations(Set<Conversation> conversations) {
        this.conversations = conversations;
    }

    public Set<Announcement> getAnnouncements() {
        return announcements;
    }

    public void setAnnouncements(Set<Announcement> announcements) {
        this.announcements = announcements;
    }
}
