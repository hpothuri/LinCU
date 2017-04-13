package com.linCu.view.backingBeans;

import com.linCu.view.utils.ADFUtils;

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
            ADFUtils.executeOperationBinding("CreateInsert"); 
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
        try {
            ADFUtils.executeOperationBinding("Commit"); 
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        this.getCreateUserPopup().hide();
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

}
