package com.linCu.view.backingBeans;

import com.linCu.view.utils.ADFUtils;

import com.linCu.view.utils.JSFUtils;
import com.linCu.view.utils.PasswordUtil;

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

import javax.faces.context.FacesContext;

import javax.faces.event.ActionEvent;

import javax.servlet.http.HttpSession;

import oracle.adf.share.ADFContext;

public class LoginBean {
    private String errorMessage;
    public LoginBean() {
        super();
    }

    public String login() {
        try {
          String password = (String)ADFUtils.getPageFlowScopeValue("password");
          System.out.println("-----Password------"+password);
            System.out.println("-----Password------"+PasswordUtil.encryptPassword(password));  
          Map paramMap = new HashMap();
          paramMap.put("password", PasswordUtil.encryptPassword(password));  
          String msg = (String) ADFUtils.executeOperationBinding("validateLogin",paramMap); 
            if("FirstLoginSuccess".equalsIgnoreCase(msg)){
                ADFUtils.setPageFlowScopeValue("passwordPage", true);
                ADFUtils.setPageFlowScopeValue("action", "first");
                //ADFUtils.setPageFlowScopeValue("firstLogin", true);
                ADFUtils.setPageFlowScopeValue("creditUnion", false); 
                //ADFUtils.setPageFlowScopeValue("resetPassword", true);
                Long userId = (Long) ADFUtils.executeOperationBinding("userCurrentRow"); 
                ADFUtils.setPageFlowScopeValue("userId", userId);
                //ADFUtils.executeOperationBinding("setUserCurrentRow"); 
                return "success";
            }else if("Success".equalsIgnoreCase(msg)){
                Map userMap = (Map) ADFUtils.executeOperationBinding("userSessionInfo"); 
                if(userMap != null && userMap.size()>0){
                    FacesContext fc = FacesContext.getCurrentInstance();
                    
                    UserSessionData userSessionData = new UserSessionData();
                    //UserSessionData userSessionData = (UserSessionData)fc.getApplication().evaluateExpressionGet(fc, "#{userSessionData}", Object.class);
                userSessionData.setUserName(userMap.get("userName").toString()); 
                userSessionData.setUserRole(userMap.get("role").toString());
                userSessionData.setUserRoleDesc(userMap.get("roleDesc").toString());
                if(userMap.get("creditUnionId") != null){
                userSessionData.setUnionId(userMap.get("creditUnionId").toString());
                JSFUtils.getSession().setAttribute("creditUnionId", userMap.get("creditUnionId").toString());
                }
                userSessionData.setUserType(userMap.get("userType").toString());
                fc.getExternalContext().getSessionMap().put("user", userSessionData);
                    JSFUtils.getSession().setAttribute("userId", userMap.get("userName").toString());   
                    JSFUtils.getSession().setAttribute("role", userMap.get("role").toString());
                    ADFUtils.setPageFlowScopeValue("passwordPage", false);
                        //ADFUtils.setPageFlowScopeValue("firstLogin", false);
                        ADFUtils.setPageFlowScopeValue("creditUnion", true); 
                        //ADFUtils.setPageFlowScopeValue("resetPassword", false);
                
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
        ADFUtils.setPageFlowScopeValue("action", "forgot");
        ADFUtils.setPageFlowScopeValue("passwordPage", true);
//        ADFUtils.setPageFlowScopeValue("firstLogin", true);
        ADFUtils.setPageFlowScopeValue("creditUnion", false); 
//        ADFUtils.setPageFlowScopeValue("resetPassword", false);
    }

    public void resetPassword(ActionEvent actionEvent) {
        ADFUtils.setPageFlowScopeValue("action", "reset");
        ADFUtils.setPageFlowScopeValue("passwordPage", true);
//        ADFUtils.setPageFlowScopeValue("firstLogin", false);
        ADFUtils.setPageFlowScopeValue("creditUnion", false); 
//        ADFUtils.setPageFlowScopeValue("resetPassword", true);
    }

    public void logout(ActionEvent actionEvent) {
        //FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        
        ADFContext.getCurrent().removeScope(ADFContext.SESSION_SCOPE);
               FacesContext fctx = FacesContext.getCurrentInstance();
               ((HttpSession) fctx.getExternalContext().getSession(false)).invalidate();
//               try {
//                   //fctx.getExternalContext().redirect("faces/login");
//               } catch (IOException e) {
//                   e.printStackTrace();
//               }
    }
}
