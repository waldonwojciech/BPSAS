package pl.wojciechwaldon.bpsas.domain.model.message;

import pl.wojciechwaldon.bpsas.domain.model.conversation.Conversation;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MESSAGE_GENERATOR")
    @SequenceGenerator(name = "MESSAGE_GENERATOR", sequenceName = "SEQUENCE_MESSAGE", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH,
            CascadeType.MERGE}, targetEntity = Conversation.class)
    @NotNull
    private Conversation conversation;

    @NotNull
    private String content;

    Message() {
    }

    Message(Builder builder) {
        this.conversation = builder.conversation;
        this.content = builder.content;
    }

    public Long getId() {
        return id;
    }

    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message = (Message) o;

        if (id != null ? !id.equals(message.id) : message.id != null) return false;
        if (conversation != null ? !conversation.equals(message.conversation) : message.conversation != null)
            return false;
        return content != null ? content.equals(message.content) : message.content == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (content != null ? content.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", content='" + content + '\'' +
                '}';
    }

    public static class Builder {

        private Conversation conversation;
        private String content;

        public Builder withConversation(@NotNull Conversation conversation) {
            this.conversation = conversation;
            return this;
        }

        public Builder withContent(@NotNull String content) {
            this.content = content;
            return this;
        }

        public Message build() {
            return new Message(this);
        }
    }
}
