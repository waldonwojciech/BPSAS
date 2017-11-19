package pl.wojciechwaldon.bpsas.domain.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @JsonIgnore
    protected Set<Conversation> conversations;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REMOVE}, mappedBy = "users")
    @JsonIgnore
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

    public void setEmail(@NotNull String email) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (conversations != null ? !conversations.equals(user.conversations) : user.conversations != null)
            return false;
        return announcements != null ? announcements.equals(user.announcements) : user.announcements == null;
    }

    @Override
    public int hashCode() {
        int result = email != null ? email.hashCode() : 0;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }
}
