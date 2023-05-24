package com.smartcontactmanager.smartcontact.entities;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jakarta.websocket.Session;

@Entity
@Table(name = "user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotBlank(message = "name should not be empty")
    @Size(min = 3, message = "name should be more than 3 charechters")
    private String name;

    @Column(unique = true)
    @Email
    private String email;

    @NotBlank
    // @Size(min = 5, message = "password should be 5 to 8 charechters")
    // @Pattern(regexp =
    // "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{5,8}$",
    // message = "password must containt 1 alphabet 1 number and @$!%*#?& ")
    private String password;

    private String image;

    @Column(length = 500)
    @NotBlank
    @Size(min = 20, message = "about should be more than 20 charechters")
    private String about;

    private Boolean enabled;

    private String role;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userEntity", fetch = FetchType.LAZY)
    private List<Contact> contact;

    public UserEntity() {
    }

    public UserEntity(int id, String name, String email, String password, String image, String about, Boolean enabled,
            String role, List<Contact> contact) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.image = image;
        this.about = about;
        this.enabled = enabled;
        this.role = role;
        this.contact = contact;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public List<Contact> getContact() {
        return contact;
    }

    public void setContact(List<Contact> contact) {
        this.contact = contact;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

//    @Override
//    public String toString() {
//        return "UserEntity [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", image="
//                + image + ", about=" + about + ", enabled=" + enabled + ", contact=" + contact + "]";
//    }
}