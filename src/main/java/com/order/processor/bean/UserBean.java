package com.order.processor.bean;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserBean implements Serializable {

    private static final long serialVersionUID = 1056260026238297037L;
    private int id;
    private String username;
    private String password;
    private String newPassword;
    private String userRole;
    private int userId;

    public UserBean() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getUserRole() {
        return userRole;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public UserBean(int id, String username, String password, String newPassword, String userRole, int userId) {
        super();
        this.id = id;
        this.username = username;
        this.password = password;
        this.newPassword = newPassword;
        this.userRole = userRole;
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "UserBean [id=" + id + ", username=" + username + ", password=" + password + ", newPassword="
                + newPassword + ", userRole=" + userRole + ", userId=" + userId + "]";
    }

}
