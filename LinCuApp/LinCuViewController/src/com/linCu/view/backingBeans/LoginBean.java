package com.linCu.view.backingBeans;

import com.linCu.view.utils.ADFUtils;

import java.util.Map;

import javax.faces.context.FacesContext;

import javax.faces.event.ActionEvent;

import oracle.adf.share.ADFContext;

public class LoginBean {
    private String errorMessage;
    public LoginBean() {
        super();
    }

    public String login() {
        try {
          String msg = (String) ADFUtils.executeOperationBinding("validateLogin"); 
            if("FirstLoginSuccess".equalsIgnoreCase(msg)){
                ADFUtils.setPageFlowScopeValue("firstLogin", true);
                ADFUtils.setPageFlowScopeValue("creditUnion", false); 
                ADFUtils.setPageFlowScopeValue("userInfo", false);
                ADFUtils.setPageFlowScopeValue("resetPassword", true);
                Long userId = (Long) ADFUtils.executeOperationBinding("userCurrentRow"); 
                ADFUtils.setPageFlowScopeValue("userId", userId);
                ADFUtils.executeOperationBinding("setUserCurrentRow"); 
                return "success";
            }else if("Success".equalsIgnoreCase(msg)){
                Map userMap = (Map) ADFUtils.executeOperationBinding("userSessionInfo"); 
                if(userMap != null && userMap.size()>0){
                    FacesContext fc = FacesContext.getCurrentInstance();
                    UserSessionData userSessionData = (UserSessionData)fc.getApplication().evaluateExpressionGet(fc, "#{userSessionData}", Object.class);
                    
//                UserSessionData userSessionData = new UserSessionData(); 
                userSessionData.setUserName(userMap.get("userName").toString()); 
                userSessionData.setUserRole(userMap.get("role").toString());
                userSessionData.setUserRoleDesc(userMap.get("roleDesc").toString());
//                ADFContext.getCurrent().getSessionScope().put("userSessionData", userSessionData);
                if(userMap.get("role") != null){
                    String role = userMap.get("role").toString();
                    if(("MSR".equalsIgnoreCase(role)) || ("SUP".equalsIgnoreCase(role))){
                        ADFUtils.setPageFlowScopeValue("firstLogin", false);
                        ADFUtils.setPageFlowScopeValue("creditUnion", true); 
                        ADFUtils.setPageFlowScopeValue("userInfo", false); 
                        ADFUtils.setPageFlowScopeValue("resetPassword", false);
                    }else if(("MAN_ADMIN".equalsIgnoreCase(role)) || ("FCB".equalsIgnoreCase(role))){
                        ADFUtils.setPageFlowScopeValue("firstLogin", false);
                        ADFUtils.setPageFlowScopeValue("creditUnion", false); 
                        ADFUtils.setPageFlowScopeValue("userInfo", true);
                        ADFUtils.setPageFlowScopeValue("resetPassword", false);
                    }
                
                }
                }
            return "success";
            }else if("InvalidLogin".equalsIgnoreCase(msg)){
                this.setErrorMessage("Invalid Password");
            }else if("InvalidUserName".equalsIgnoreCase(msg)){
                this.setErrorMessage("User Not Registered");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void forgotPassword(ActionEvent actionEvent) {
        ADFUtils.setPageFlowScopeValue("firstLogin", false);
        ADFUtils.setPageFlowScopeValue("creditUnion", false); 
        ADFUtils.setPageFlowScopeValue("userInfo", false);
        ADFUtils.setPageFlowScopeValue("resetPassword", false);
    }

    public void resetPassword(ActionEvent actionEvent) {
        ADFUtils.setPageFlowScopeValue("firstLogin", false);
        ADFUtils.setPageFlowScopeValue("creditUnion", false); 
        ADFUtils.setPageFlowScopeValue("userInfo", false);
        ADFUtils.setPageFlowScopeValue("resetPassword", true);
    }
}
