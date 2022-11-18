package pojo;

import lombok.*;
import org.apache.commons.lang3.RandomStringUtils;

public class User {

    @Getter
    @Setter
    private String email, password, name;
    public User() {
    }
    public User(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }
    public static String generateRandomEmail() {
        return RandomStringUtils.randomAlphanumeric(16) + "@yandex.ru";
    }
    public static String generateRandomString() {
        return RandomStringUtils.randomAlphanumeric(10);
    }

    public static User createRandomUser() {
        return new User(generateRandomEmail(), generateRandomString(), generateRandomString());
    }

    public static User createUserWithoutName() {
        return new User(generateRandomEmail(), generateRandomString(), null);
    }

    public static User createUserWithoutEmail() {
        return new User(null, generateRandomString(), generateRandomString());
    }

    public static User createUserWithoutPassword() {
        return new User(generateRandomEmail(), null, generateRandomString());
    }

    public static User loginData(User user) {
        return new User(user.getEmail(), user.getPassword(), null);
    }

    public static User password() {
        return new User(null, generateRandomString(), null);
    }
}
