package ku.cs.models.users;

import at.favre.lib.crypto.bcrypt.BCrypt;
import ku.cs.config.Configuration;
import java.time.LocalDateTime;
import java.util.UUID;

public class User {
    private String nameTitle;
    private String username;
    private String hashedPassword;
    private String name;
    private String surname;
    private boolean status;
    private boolean activated;
    private final String role;
    private LocalDateTime recentTime;
    private String profilePictureFileName;

    public User(String username, String password, String nameTitle, String name, String surname, String role) {
        this.username = username;
        this.hashedPassword = BCrypt.withDefaults().hashToString(12, password.toCharArray());
        this.nameTitle = nameTitle;
        this.name = name;
        this.surname = surname;
        this.status = true;
        this.activated = false;
        this.role = role;
        this.recentTime = null;
        this.profilePictureFileName = Configuration.getConfiguration().getDefaultProfilePictureFileName();
    }

    public User(String username, String hashedPassword, String nameTitle, String name, String surname, String role, LocalDateTime recentTime, boolean status, boolean activated, String profilePictureFileName) {
        this.username = username;
        this.hashedPassword = hashedPassword;
        this.nameTitle = nameTitle;
        this.name = name;
        this.surname = surname;
        this.role = role;
        this.recentTime = recentTime;
        this.status = status;
        this.activated = activated;
        this.profilePictureFileName = profilePictureFileName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getRole() {
        return role;
    }

    public String getRecentTimeString() {
        if (this.recentTime != null) {
            return recentTime.toString();
        }
        return "null";
    }

    public LocalDateTime getRecentTime() {
        return recentTime;
    }

    public void setRecentTime(LocalDateTime recentTime) {
        this.recentTime = recentTime;
    }

    public String getProfilePictureFileName() {
        return profilePictureFileName;
    }

    protected void setPassword(String password) {
        this.hashedPassword = BCrypt.withDefaults().hashToString(12, password.toCharArray());
    }

    public void setDefaultPassword(String password) {
        if (!this.activated) {
            this.setPassword(password);
        }
    }

    public void setProfilePictureFileName() {
        this.profilePictureFileName = UUID.randomUUID() + "-image.jpg";
    }

    public boolean isUsername(String username) {
        return this.username.equals(username);
    }

    public boolean validatePassword(String password) {
        BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), this.hashedPassword);
        return result.verified;
    }

    public void changePassword(String oldPassword, String newPassword) {
        if (this.validatePassword(oldPassword)) {
            this.setPassword(newPassword);
        } else {
            throw new IllegalArgumentException("Invalid password");
        }
    }

    public String getNameTitle() {
        return nameTitle;
    }

    public void setNameTitle(String nameTitle) {
        this.nameTitle = nameTitle;
    }

    public boolean getActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public void resetProfilePictureFileName() {
        this.profilePictureFileName = Configuration.getConfiguration().getDefaultProfilePictureFileName();
    }

    public final String getFullName() {
        return nameTitle + name + " " + surname;
    }
}