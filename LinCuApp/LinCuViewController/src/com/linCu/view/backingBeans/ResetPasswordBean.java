package com.linCu.view.backingBeans;

import com.linCu.constants.LinCUConstants;
import com.linCu.view.utils.ADFUtils;
import com.linCu.view.utils.EmailUtil;
import com.linCu.view.utils.PasswordUtil;

import java.util.HashMap;
import java.util.Map;

public class ResetPasswordBean {
    private String errorMessage;
    public ResetPasswordBean() {
        super();
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String resetPassword() {
        try {
            Long userId = (Long) ADFUtils.executeOperationBinding("userCurrentRow"); 
            if(userId != null){
            ADFUtils.setPageFlowScopeValue("userId", userId);
            
            String oldPassword = (String)ADFUtils.getPageFlowScopeValue("oldPassword");
            String newPassword = (String)ADFUtils.getPageFlowScopeValue("newPassword");
            String confirmNewPassword = (String)ADFUtils.getPageFlowScopeValue("confirmPassword");
            String userName = (String)ADFUtils.getPageFlowScopeValue("resetUserName");  
            Map paramMap = new HashMap();
            paramMap.put("oldPassword", PasswordUtil.encryptPassword(oldPassword));
            paramMap.put("newPassword", PasswordUtil.encryptPassword(newPassword));
            paramMap.put("confirmPassword", PasswordUtil.encryptPassword(confirmNewPassword));
            paramMap.put("userId", userId);
            String msg = (String) ADFUtils.executeOperationBinding("resetPassword",paramMap); 
            System.out.println("-----Msg--"+msg);
            if("Success".equalsIgnoreCase(msg)){
                return "success";
            }else if("InvalidOldPassword".equalsIgnoreCase(msg)){
                this.setErrorMessage("Invalid Old Password");
            }else if("MismatchInConfirmPassword".equalsIgnoreCase(msg)){
                this.setErrorMessage("Mismatch In Confirm Password");
            }
            }else{
                this.setErrorMessage("User does not exists");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public String firstTimeReset() {
        try {
            ADFUtils.executeOperationBinding("setUserCurrentRow"); 
            String newPassword = (String)ADFUtils.getPageFlowScopeValue("newPassword");
            String confirmNewPassword = (String)ADFUtils.getPageFlowScopeValue("confirmPassword");
            Long userId = (Long)ADFUtils.getPageFlowScopeValue("userId");
            Map paramMap = new HashMap();
            paramMap.put("newPassword", PasswordUtil.encryptPassword(newPassword));
            paramMap.put("confirmPassword", PasswordUtil.encryptPassword(confirmNewPassword));
            paramMap.put("userId", userId);
          String msg = (String) ADFUtils.executeOperationBinding("firstTimeResetPassword", paramMap); 
            System.out.println("-----Msg--"+msg);
            if("Success".equalsIgnoreCase(msg)){
                return "success";
            }else if("MismatchInConfirmPassword".equalsIgnoreCase(msg)){
                this.setErrorMessage("Mismatch In Confirm Password");
            }
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public String forgotPassword() {
        Long userId = (Long) ADFUtils.executeOperationBinding("userCurrentRow");      
        if(userId != null){
        ADFUtils.setPageFlowScopeValue("userId", userId);

        String userName = (String)ADFUtils.getPageFlowScopeValue("resetUserName");  
        String password = PasswordUtil.generateRamdomPassword();
        Map paramMap = new HashMap();
        paramMap.put("password", PasswordUtil.encryptPassword(password));
        paramMap.put("userId", userId);
        ADFUtils.executeOperationBinding("forgotResetPassword",paramMap);
        ADFUtils.executeOperationBinding("Commit"); 
        String subject  = "Your password is reset";
        String content = "Dear User," + "\n\n Your LinCU account password has been reset, You can access system using below credentials.\n\n Login Name: ".concat(userName).concat("\n\n Password: ").concat(password);
        EmailUtil.sendEmail(LinCUConstants.EMAIL_USER, userName, subject, content);
            return "success";
        }else{
        this.setErrorMessage("User does not exists");   
        }
        return null;
    }
}
