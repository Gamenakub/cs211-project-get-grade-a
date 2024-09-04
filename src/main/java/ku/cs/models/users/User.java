package ku.cs.models.users;

import at.favre.lib.crypto.bcrypt.BCrypt;

import java.time.LocalDateTime;

public class User {
    private String username;
    private String password;
    private String name;
    private String surname;
    private boolean status;
    private String role;
    private LocalDateTime recentTime;
    private String imagePath;
    private static final String defaultImagePath = "data/images/profile-pictures/default-image.png";

    public User(String username, String password, String name, String surname) {
        this.username = username;
        this.password = BCrypt.withDefaults().hashToString(12, password.toCharArray());
        this.name = name;
        this.surname = surname;
        this.recentTime = LocalDateTime.now();
        this.status = true;
        this.imagePath = defaultImagePath;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public boolean getStatus() {
        return status;
    }

    public String getRole() {
        return role;
    }

    public LocalDateTime getRecentTime() {
        return recentTime;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = BCrypt.withDefaults().hashToString(12, password.toCharArray());
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setRecentTime(LocalDateTime recentTime) {
        this.recentTime = recentTime;
    }

    public void setImagePath() {
        this.imagePath = "data/images/profile-pictures/" + this.username + "-image.png";
    }

    public boolean isUsername(String username) {
        return this.username.equals(username);
    }

    public boolean validatePassword(String password) {
        BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), this.password);
        return result.verified;
    }

    public void changePassword(String oldPassword, String newPassword) {
        if(this.validatePassword(oldPassword)){
            this.setPassword(newPassword);
        }
    }

}

/*
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = now.format(formatter);
 */