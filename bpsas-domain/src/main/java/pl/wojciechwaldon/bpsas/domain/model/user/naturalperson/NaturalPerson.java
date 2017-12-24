package pl.wojciechwaldon.bpsas.domain.model.user.naturalperson;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import pl.wojciechwaldon.bpsas.domain.model.announcement.Announcement;
import pl.wojciechwaldon.bpsas.domain.model.conversation.Conversation;
import pl.wojciechwaldon.bpsas.domain.model.user.User;
import pl.wojciechwaldon.bpsas.domain.validator.user.naturalperson.NaturalPersonAttributesValidator;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import java.util.Set;

@JsonAutoDetect
@Entity
public class NaturalPerson extends User {

    @JsonProperty
    @NotNull
    private String firstName;

    @JsonProperty
    @NotNull
    private String lastName;

    public NaturalPerson() {
    }

    NaturalPerson(Builder builder) {
        super(builder.email, builder.password, builder.conversations, builder.announcements, builder.friends, builder.sentFriendRequests, builder.receivedFriendRequests);
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;

        NaturalPersonAttributesValidator.validate(this);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NaturalPerson that = (NaturalPerson) o;

        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        return email != null ? email.equals(that.email) : that.email == null;
    }

    @Override
    public String toString() {
        return "NaturalPerson{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", conversations=" + conversations +
                ", announcements=" + announcements +
                '}';
    }

    public static class Builder {

        private String email;
        private String password;
        private String firstName;
        private String lastName;
        private Set<Conversation> conversations;
        private Set<Announcement> announcements;
        private Set<User> friends;
        private Set<User> sentFriendRequests;
        private Set<User> receivedFriendRequests;

        public Builder withEmail(@NotNull String email) {
            this.email = email;
            return this;
        }

        public Builder withPassword(@NotNull String password) {
            this.password = password;
            return this;
        }

        public Builder withFirstName(@NotNull String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder withLastName(@NotNull String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder withConversations(Set<Conversation> conversations) {
            this.conversations = conversations;
            return this;
        }

        public Builder withAnnouncements(Set<Announcement> announcements) {
            this.announcements = announcements;
            return this;
        }

        public Builder withFriends(Set<User> friends) {
            this.friends = friends;
            return this;
        }

        public Builder withSentFriendRequests(Set<User> sentFriendRequests) {
            this.sentFriendRequests = sentFriendRequests;
            return this;
        }

        public Builder withReceivedFriendRequests(Set<User> receivedFriendRequests) {
            this.receivedFriendRequests = receivedFriendRequests;
            return this;
        }

        public NaturalPerson build() {
            return new NaturalPerson(this);
        }

    }
}
