package pl.wojciechwaldon.bpsas.domain.model.user.company;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import pl.wojciechwaldon.bpsas.domain.model.announcement.Announcement;
import pl.wojciechwaldon.bpsas.domain.model.conversation.Conversation;
import pl.wojciechwaldon.bpsas.domain.model.user.User;
import pl.wojciechwaldon.bpsas.domain.validator.user.company.CompanyAttributesValidator;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import java.util.Set;

@JsonAutoDetect
@Entity
public class Company extends User {

    @JsonProperty
    @NotNull
    private String companyName;

    public Company() {
    }

    public Company(Builder builder) {
        super(builder.email, builder.password, builder.imageUrl, builder.conversations, builder.announcements, builder.aboutMe);
        this.companyName = builder.companyName;

        CompanyAttributesValidator.validate(this);
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Company that = (Company) o;

        return email != null ? email.equals(that.email) : that.email == null;
    }

    @Override
    public String toString() {
        return "Company{" +
                "companyName='" + companyName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", conversations=" + conversations +
                ", announcements=" + announcements +
                '}';
    }

    public static class Builder {

        private String email;
        private String password;
        private String imageUrl = "default.png";
        private String companyName;
        private Set<Conversation> conversations;
        private Set<Announcement> announcements;
        private String aboutMe = "";

        public Builder withEmail(@NotNull String email) {
            this.email = email;
            return this;
        }

        public Builder withPassword(@NotNull String password) {
            this.password = password;
            return this;
        }

        public Builder withImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public Builder withCompanyName(@NotNull String companyName) {
            this.companyName = companyName;
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

        public Company build() {
            return new Company(this);
        }

    }
}
