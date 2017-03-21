package org.hashtag.rms.model;

import javax.persistence.*;

/**
 * Created by Buddhi on 2/19/2017.
 */
@Entity
@Table(name = "User")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserID")
    private Integer userId;

    @Column(name = "Username")
    private String username;

    @Column(name = "Password")
    private String password;

    @Column(name = "Type")
    private Integer type;

    @Column(name = "Name")
    private String name;

    @Column(name = "Nic")
    private String nic;

    @Column(name = "Telephone")
    private String telephone;

    public User() {

    }

    public User(String username, String password, Integer type, String name, String nic, String telephone) {
        this.username = username;
        this.password = password;
        this.type = type;
        this.name = name;
        this.nic = nic;
        this.telephone = telephone;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
