package com.linCu.view.backingBeans;

import com.linCu.view.utils.ADFUtils;

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
          String msg = (String) ADFUtils.executeOperationBinding("resetPassword"); 
            System.out.println("-----Msg--"+msg);
            if("Success".equalsIgnoreCase(msg)){
                return "success";
            }else if("InvalidOldPassword".equalsIgnoreCase(msg)){
                this.setErrorMessage("Invalid Old Password");
            }else if("MismatchInConfirmPassword".equalsIgnoreCase(msg)){
                this.setErrorMessage("Mismatch In Confirm Password");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
