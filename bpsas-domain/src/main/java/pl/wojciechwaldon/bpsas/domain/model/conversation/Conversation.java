package pl.wojciechwaldon.bpsas.domain.model.conversation;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import pl.wojciechwaldon.bpsas.domain.model.message.Message;
import pl.wojciechwaldon.bpsas.domain.model.user.User;
import pl.wojciechwaldon.bpsas.domain.validator.conversation.ConversationAttributesValidator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Entity
public class Conversation {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CONVERSATION_GENERATOR")
    @SequenceGenerator(name = "CONVERSATION_GENERATOR", sequenceName = "SEQUENCE_CONVERSATION", allocationSize = 1)
    private Long id;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
    @NotNull
    @JsonIgnore
    private Set<User> users;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "conversation", orphanRemoval = true)
    @NotNull
    @JsonBackReference
    private List<Message> messages;

    Conversation() {
    }

    public Conversation(Builder builder) {
        this.users = builder.users;
        this.messages = builder.messages;

        ConversationAttributesValidator.validate(this);
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

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Conversation that = (Conversation) o;

        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (messages != null ? messages.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Conversation{" +
                "id=" + id +
                ", messages=" + messages +
                '}';
    }

    public static class Builder {

        private Set<User> users;
        private List<Message> messages;

        public Builder withUsers(@NotNull Set<User> users) {
            this.users = users;
            return this;
        }

        public Builder withMessages(@NotNull List<Message> messages) {
            this.messages = messages;
            return this;
        }

        public Conversation build() {
            return new Conversation(this);
        }

    }
}
