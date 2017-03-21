package org.hashtag.rms.resource;

/**
 * Created by Buddhi on 2/19/2017.
 */
public class UserResource {

    private String username;
    private int id;
    private String password;
    private String name;
    private int type;
    private String nic;
    private String telephone;

    public UserResource() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserResource(int id, String username, String password, String name, int type, String nic, String telephone) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.type = type;
        this.nic = nic;
        this.telephone = telephone;

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
