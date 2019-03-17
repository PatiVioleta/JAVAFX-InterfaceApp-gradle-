package model;

import java.util.Objects;
import repository.HasId;

public class User implements HasId<Integer> {
    private Integer userId;
    private String name;
    private String password;


    public User(Integer userId, String name, String password) {
        this.userId = userId;
        this.name = name;
        this.password = password;
    }


    public Integer getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userId, user.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", nume='" + name + '\'' +
                ", parola='" + password + '\'' +
                '}';
    }

    @Override
    public Integer getId() {
        return userId;
    }

    @Override
    public void setId(Integer integer) {
        userId = integer;
    }
}
