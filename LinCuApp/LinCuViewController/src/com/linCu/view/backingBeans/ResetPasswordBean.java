package com.linCu.view.backingBeans;

import com.linCu.constants.LinCUConstants;
import com.linCu.view.utils.ADFUtils;
import com.linCu.view.utils.EmailUtil;
import com.linCu.view.utils.JSFUtils;
import com.linCu.view.utils.PasswordUtil;

import java.util.HashMap;
import java.util.Map;

import java.util.Random;

import org.apache.commons.lang.RandomStringUtils;

public class ResetPasswordBean {
    private String errorMessage;
    private Boolean renderSecQstn;
    private String secQstnString;
    public ResetPasswordBean() {
        super();
        this.secQstnString = generateRamdomPassword();
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String resetPassword() {
        try {          
            //Long userId = (Long) ADFUtils.executeOperationBinding("userCurrentRow"); 
            //if(userId != null){
            //ADFUtils.setPageFlowScopeValue("userId", userId);
            //Map secParamMap = new HashMap();
            //secParamMap.put("userId", userId);
            String isAnsValid = (String)ADFUtils.executeOperationBinding("validateSecQustns"); 
            if("failure".equalsIgnoreCase(isAnsValid)){
            //JSFUtils.addErrorMessage("Authentication Failed");
            this.setErrorMessage("Authentication Failed");
            return null;
            }
            String oldPassword = (String)ADFUtils.getPageFlowScopeValue("oldPassword");
            String newPassword = (String)ADFUtils.getPageFlowScopeValue("newPassword");
            String confirmNewPassword = (String)ADFUtils.getPageFlowScopeValue("confirmPassword"); 
            Map paramMap = new HashMap();
            paramMap.put("oldPassword", PasswordUtil.encryptPassword(oldPassword));
            paramMap.put("newPassword", PasswordUtil.encryptPassword(newPassword));
            paramMap.put("confirmPassword", PasswordUtil.encryptPassword(confirmNewPassword));
            String msg = (String) ADFUtils.executeOperationBinding("resetPassword",paramMap); 
            System.out.println("-----Msg--"+msg);
            if("Success".equalsIgnoreCase(msg)){
                return "success";
            }else if("InvalidOldPassword".equalsIgnoreCase(msg)){
                this.setErrorMessage("Invalid Old Password");
            }else if("MismatchInConfirmPassword".equalsIgnoreCase(msg)){
                this.setErrorMessage("Mismatch In Confirm Password");
            }
//            }else{
//                this.setErrorMessage("User does not exists");
//            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public String firstTimeReset() {
        try {
           // ADFUtils.executeOperationBinding("setUserCurrentRow"); 
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
            }else if("duplicateNotAllowed".equalsIgnoreCase(msg)){
                this.setErrorMessage("Setting duplicate security questions not allowed");
            }
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public String forgotPassword() {
//        Long userId = (Long) ADFUtils.executeOperationBinding("userCurrentRow");      
//        if(userId != null){
//        ADFUtils.setPageFlowScopeValue("userId", userId); 
//        Map secParamMap = new HashMap();
//        secParamMap.put("userId", userId);
        String isAnsValid = (String)ADFUtils.executeOperationBinding("validateSecQustns"); 
        if("failure".equalsIgnoreCase(isAnsValid)){
        //JSFUtils.addErrorMessage("Authentication Failed");
        this.setErrorMessage("Authentication Failed");
        return null;
        }
            
        String userName = (String)ADFUtils.getPageFlowScopeValue("resetUserName");  
        String password = PasswordUtil.generateRamdomPassword();
        Map paramMap = new HashMap();
        paramMap.put("password", PasswordUtil.encryptPassword(password));
        //paramMap.put("userId", userId);
        ADFUtils.executeOperationBinding("forgotResetPassword",paramMap);
        ADFUtils.executeOperationBinding("Commit"); 
        String subject  = "Your password is reset";
        String content = "Dear User," + "\n\n Your LinCU account password has been reset, You can access system using below credentials.\n\n Login Name: ".concat(userName).concat("\n\n Password: ").concat(password);
        EmailUtil.sendEmail(LinCUConstants.EMAIL_USER, userName, subject, content);
            return "success";
//        }else{
//        this.setErrorMessage("User does not exists");   
//        }
        //return null;
    }

    public void setRenderSecQstn(Boolean renderSecQstn) {
        this.renderSecQstn = renderSecQstn;
    }

    public Boolean getRenderSecQstn() {
        return renderSecQstn;
    }

    public void setSecQstnString(String secQstnString) {
        this.secQstnString = secQstnString;
    }

    public String getSecQstnString() {
        return secQstnString;
    }

    public static String generateRamdomPassword() {
        String characters = "";
        Random randomGenerator = new Random();
        while (characters.length() < 3) {

            int random = randomGenerator.nextInt(6);
            if ((!characters.contains(String.valueOf(random))) && (random != 0)){
                characters = characters.concat(String.valueOf(random));
            }
        }
        System.out.println( "-------------Random Password------------------"+characters );
        return characters;
    }

    public String validateUser() {
        Long userId = (Long) ADFUtils.executeOperationBinding("userCurrentRow");      
        if(userId != null){
        ADFUtils.setPageFlowScopeValue("userId", userId);
//        Map paramMap = new HashMap();
//        paramMap.put("userId", userId);
//        ADFUtils.executeOperationBinding("setUserCurrentRow",paramMap); 
        }else{
        this.setErrorMessage("User does not exists"); 
        return null;
        }
        return "continue";
    }
}
