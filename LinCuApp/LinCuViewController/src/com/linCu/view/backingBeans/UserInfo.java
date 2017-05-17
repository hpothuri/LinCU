package com.linCu.view.backingBeans;

import com.linCu.constants.LinCUConstants;
import com.linCu.view.utils.ADFUtils;

import com.linCu.view.utils.EmailUtil;

import com.linCu.view.utils.JSFUtils;
import com.linCu.view.utils.PasswordUtil;

import java.util.HashMap;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.faces.event.ValueChangeEvent;

import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.context.AdfFacesContext;

public class UserInfo {
    private RichPopup createUserPopup;
    private Boolean showbranch = true;
    private RichSelectOneChoice creditUniionBranch;
    private RichPopup deleteUserPopup;

    public UserInfo() {
        super();
    }

    public void setCreateUserPopup(RichPopup createUserPopup) {
        this.createUserPopup = createUserPopup;
    }

    public RichPopup getCreateUserPopup() {
        return createUserPopup;
    }

    public void createUser(ActionEvent actionEvent) {
        try {
            ADFUtils.executeOperationBinding("createUser"); 
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        this.getCreateUserPopup().show(hints);
    }

    public void editUser(ActionEvent actionEvent) {
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        this.getCreateUserPopup().show(hints);
    }

    public void save(ActionEvent actionEvent) {
            String userType = (String)ADFUtils.getBoundAttributeValue("UserTypeCode1");
            Object creditUnionId = ADFUtils.getBoundAttributeValue("CreditUnionId");
            Object creditUnionBranchId = ADFUtils.getBoundAttributeValue("CreditUnionBranchId");
            if(((userType != null) && ("CREDIT_UNION".equalsIgnoreCase(userType))) && ((creditUnionId == null) || (creditUnionBranchId == null))){
            if(creditUnionId == null)
            JSFUtils.addErrorMessage("Credit Union is required");
            if(creditUnionBranchId == null)
            JSFUtils.addErrorMessage("Branch is required");
            }else{
        try {
            String action = (String)ADFUtils.getPageFlowScopeValue("action");
            if((action != null) && ("create".equalsIgnoreCase(action))){
            String userName = (String)ADFUtils.getBoundAttributeValue("Email");
            String password = PasswordUtil.generateRamdomPassword();
            Map paramMap = new HashMap();
            paramMap.put("password", PasswordUtil.encryptPassword(password));
            ADFUtils.executeOperationBinding("updatePassword",paramMap);
            String subject  = "Your LinCU Account is created";
            String content = "Dear User," + "\n\n Your LinCU account has been created, You can access system using below credentials.\n\n Login Name: ".concat(userName).concat("\n\n Password: ").concat(password);
            EmailUtil.sendEmail(LinCUConstants.EMAIL_USER, userName, subject, content);
            }
            ADFUtils.executeOperationBinding("updateUser"); 
            ADFUtils.executeOperationBinding("Commit"); 

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        this.getCreateUserPopup().hide();
        }
    }

    public void cancel(ActionEvent actionEvent) {
        try {
            ADFUtils.executeOperationBinding("Rollback"); 
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        this.getCreateUserPopup().hide();
    }

    public void changeCreditUniion(ValueChangeEvent valueChangeEvent) {
        if(valueChangeEvent.getNewValue() != null){
            this.setShowbranch(false);
        }
        //AdfFacesContext.getCurrentInstance().addPartialTarget(getCreditUniionBranch());
    }

    public void setShowbranch(Boolean showbranch) {
        this.showbranch = showbranch;
    }

    public Boolean getShowbranch() {
        return showbranch;
    }

    public void setCreditUniionBranch(RichSelectOneChoice creditUniionBranch) {
        this.creditUniionBranch = creditUniionBranch;
    }

    public RichSelectOneChoice getCreditUniionBranch() {
        return creditUniionBranch;
    }

    public void updateBranch(ValueChangeEvent valueChangeEvent) {
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
    }

    public void setDeleteUserPopup(RichPopup deleteUserPopup) {
        this.deleteUserPopup = deleteUserPopup;
    }

    public RichPopup getDeleteUserPopup() {
        return deleteUserPopup;
    }

    public void openDeleteUserPopup(ActionEvent actionEvent) {
        try {
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                this.getDeleteUserPopup().show(hints);   
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void deleteUser(ActionEvent actionEvent) {
        try {
            ADFUtils.executeOperationBinding("Delete"); 
            ADFUtils.executeOperationBinding("Commit"); 
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        this.getDeleteUserPopup().hide();
    }

    public void changeUserTypeCode(ValueChangeEvent valueChangeEvent) {
        System.out.println("---------------newValue--------"+valueChangeEvent.getNewValue());
        valueChangeEvent.getComponent().processUpdates(FacesContext.getCurrentInstance());
    }
}
