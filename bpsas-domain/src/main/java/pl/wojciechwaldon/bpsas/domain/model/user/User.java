package pl.wojciechwaldon.bpsas.domain.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import pl.wojciechwaldon.bpsas.domain.model.announcement.Announcement;
import pl.wojciechwaldon.bpsas.domain.model.conversation.Conversation;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class User implements Serializable {

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

    @ElementCollection(targetClass = User.class)
    protected Set<User> friends;

    @ElementCollection(targetClass = User.class)
    protected Set<User> sentFriendRequests;

    @ElementCollection(targetClass = User.class)
    protected Set<User> receivedFriendResuests;

    protected User() {
    }

    protected User(String email, @NotNull String password, Set<Conversation> conversations, Set<Announcement> announcements, Set<User> friends, Set<User> sentFriendRequests, Set<User> receivedFriendResuests) {
        this.email = email;
        this.password = password;
        this.conversations = conversations;
        this.announcements = announcements;
        this.friends = friends;
        this.sentFriendRequests = sentFriendRequests;
        this.receivedFriendResuests = receivedFriendResuests;
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

    public Set<User> getFriends() {
        return friends;
    }

    public void setFriends(Set<User> friends) {
        this.friends = friends;
    }

    public Set<User> getSentFriendRequests() {
        return sentFriendRequests;
    }

    public void setSentFriendRequests(Set<User> sentFriendRequests) {
        this.sentFriendRequests = sentFriendRequests;
    }

    public Set<User> getReceivedFriendResuests() {
        return receivedFriendResuests;
    }

    public void setReceivedFriendResuests(Set<User> receivedFriendResuests) {
        this.receivedFriendResuests = receivedFriendResuests;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(email, user.email) &&
                Objects.equals(password, user.password) &&
                Objects.equals(conversations, user.conversations) &&
                Objects.equals(announcements, user.announcements) &&
                Objects.equals(friends, user.friends) &&
                Objects.equals(sentFriendRequests, user.sentFriendRequests) &&
                Objects.equals(receivedFriendResuests, user.receivedFriendResuests);
    }

    @Override
    public int hashCode() {

        return Objects.hash(email);
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", conversations=" + conversations +
                ", announcements=" + announcements +
                ", friends=" + friends +
                ", sentFriendRequests=" + sentFriendRequests +
                ", receivedFriendResuests=" + receivedFriendResuests +
                '}';
    }
}
