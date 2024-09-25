package ku.cs.models.users;

import at.favre.lib.crypto.bcrypt.BCrypt;

import java.time.LocalDateTime;

public class User {
    private String nameTitle;
    private String username;
    private String hashedPassword;
    private String name;
    private String surname;
    private boolean status;
    private boolean activated;
    private String role;
    private LocalDateTime recentTime;
    private String profilePictureFileName;
    public static final String defaultProfilePictureFileName = "default-image.jpg";

    //constructor สำหรับสร้าง object ใหม่
    public User(String username, String password,String nameTitle, String name, String surname, String role) {
        this.username = username;
        this.hashedPassword = BCrypt.withDefaults().hashToString(12, password.toCharArray());
        this.nameTitle = nameTitle;
        this.name = name;
        this.surname = surname;
        this.status = true;
        this.activated = false;
        this.role = role;
        this.recentTime = LocalDateTime.now();
        this.profilePictureFileName = defaultProfilePictureFileName;
    }

    //constructor สำหรับดึง object เดิม
    public User(String username, String hashedPassword,String nameTitle, String name, String surname, String role, String recentTimeString, boolean status, boolean activated, String profilePictureFileName) {
        this.username = username;
        this.hashedPassword = hashedPassword;
        this.nameTitle = nameTitle;
        this.name = name;
        this.surname = surname;
        this.role = role;
        this.recentTime = LocalDateTime.parse(recentTimeString);
        this.status = status;
        this.activated = activated;
        this.profilePictureFileName = profilePictureFileName;
    }


    public String getUsername() {
        return username;
    }

    public String getHashedPassword() {
        return hashedPassword;
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

    public String getRecentTimeString() {
        return recentTime.toString();
    }

    public LocalDateTime getRecentTime() {
        return recentTime;
    }

    public String getProfilePictureFileName() {
        return profilePictureFileName;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.hashedPassword = BCrypt.withDefaults().hashToString(12, password.toCharArray());
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

    public void setRecentTime(String recentTimeString) {
        this.recentTime = LocalDateTime.parse(recentTimeString);
    }

    public void setProfilePictureFileName() {
        this.profilePictureFileName = this.username + "-image.jpg";
    }

    public boolean isUsername(String username) {
        return this.username.equals(username);
    }

    public boolean validatePassword(String password) {
        BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), this.hashedPassword);
        return result.verified;
    }

    public void changePassword(String oldPassword, String newPassword) {
        if(this.validatePassword(oldPassword)){
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
        this.profilePictureFileName = defaultProfilePictureFileName;
    }
}