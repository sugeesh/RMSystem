package org.hashtag.rms.resource;

/**
 * Created by Buddhi on 2/19/2017.
 */
public class UserResource {

    private String username;
    private String password;
    private String name;
    private int type;

    public UserResource() {
    }

    public UserResource(String username, String password, String name, int type) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.type = type;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
