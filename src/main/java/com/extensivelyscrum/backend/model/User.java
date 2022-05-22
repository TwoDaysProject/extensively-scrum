package com.extensivelyscrum.backend.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID",  strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @NotNull(message = "Email should not be null!")
    @NotEmpty(message = "Email must not be an empty string")
    @Email(message = "Not an email")
    private String email;

    @NotNull(message = "Password should not be null!")
    @NotEmpty(message = "Password must not be an empty string")
    private String password;

    @NotNull(message = "fullName should not be null!")
    @NotEmpty(message = "fullName must not be an empty string")
    private String fullName;

    @NotNull(message = "jobTitle should not be null!")
    @NotEmpty(message = "jobTitle must not be an empty string")
    private boolean jobTitle;

    private String imagePath;

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {return fullName; }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public boolean isJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(boolean jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
