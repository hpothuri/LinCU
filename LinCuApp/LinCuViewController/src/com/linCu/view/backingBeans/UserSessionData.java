package com.linCu.view.backingBeans;

public class UserSessionData {
    private String userName;
    private String userRole;
    private String userRoleDesc;
    public UserSessionData() {
        super();
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRoleDesc(String userRoleDesc) {
        this.userRoleDesc = userRoleDesc;
    }

    public String getUserRoleDesc() {
        return userRoleDesc;
    }
}
