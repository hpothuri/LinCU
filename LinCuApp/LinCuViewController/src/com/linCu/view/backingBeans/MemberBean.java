package com.linCu.view.backingBeans;

import com.linCu.view.utils.ADFUtils;

import com.linCu.view.utils.JSFUtils;

import javax.faces.event.ActionEvent;

import oracle.adf.view.rich.component.rich.RichPopup;

public class MemberBean {
    private RichPopup confirmDeletePopup;

    public MemberBean() {
        super();
    }

    public void save(ActionEvent actionEvent) {
        try {
            ADFUtils.executeOperationBinding("updateMember"); 
            ADFUtils.executeOperationBinding("Commit"); 
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void deleteConfirm(ActionEvent actionEvent) {
        try {
            Boolean deleteCreditUnionAllowed = (Boolean)ADFUtils.executeOperationBinding("deleteMemeberAllowed");
            if(deleteCreditUnionAllowed){
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                this.getConfirmDeletePopup().show(hints);   
            }else{
                JSFUtils.addErrorMessage("Cannot delete the member. This member has cards.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setConfirmDeletePopup(RichPopup confirmDeletePopup) {
        this.confirmDeletePopup = confirmDeletePopup;
    }

    public RichPopup getConfirmDeletePopup() {
        return confirmDeletePopup;
    }

    public void deleteMember(ActionEvent actionEvent) {
        try {
            ADFUtils.executeOperationBinding("Delete"); 
            ADFUtils.executeOperationBinding("Commit"); 
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        this.getConfirmDeletePopup().hide();
    }
}
