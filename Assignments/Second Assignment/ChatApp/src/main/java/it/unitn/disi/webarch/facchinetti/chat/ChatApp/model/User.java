package it.unitn.disi.webarch.facchinetti.chat.ChatApp.model;

import org.jetbrains.annotations.NotNull;

public class User {

    // Once set they will never be updated
    private final String username;
    private final String password;
    private final Role role;


    public User(@NotNull String username, @NotNull String password, @NotNull Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!username.equals(user.username)) return false;
        if (!password.equals(user.password)) return false;
        return role == user.role;
    }

    @Override
    public int hashCode() {
        int result = username.hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + role.hashCode();
        return result;
    }
}
