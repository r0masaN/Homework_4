package romasan.homework_4.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum UserRole {
    ADMIN("r_admin"),
    PREMIUM_USER("r_premium_user"),
    GUEST("r_guest");

    private final String str;

    UserRole(final String str) {
        this.str = str;
    }

    @JsonProperty
    public String getStr() {
        return this.str;
    }
}
