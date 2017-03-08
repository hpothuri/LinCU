package com.linCu.view.backingBeans;

import com.linCu.view.utils.ADFUtils;

import javax.faces.event.ActionEvent;

import oracle.adf.view.rich.component.rich.RichPopup;

public class CreditUnionBranches {
    private RichPopup creditUnionBranchPopup;

    public CreditUnionBranches() {
        super();
    }

    public void setCreditUnionBranchPopup(RichPopup creditUnionBranchPopup) {
        this.creditUnionBranchPopup = creditUnionBranchPopup;
    }

    public RichPopup getCreditUnionBranchPopup() {
        return creditUnionBranchPopup;
    }

    public void createCreditUnionBranch(ActionEvent actionEvent) {
        try {
            ADFUtils.executeOperationBinding("CreateInsert"); 
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        this.getCreditUnionBranchPopup().show(hints);
    }

    public void editCreditUnionBranch(ActionEvent actionEvent) {
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        this.getCreditUnionBranchPopup().show(hints);
    }

    public void save(ActionEvent actionEvent) {
        try {
            ADFUtils.executeOperationBinding("Commit"); 
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        this.getCreditUnionBranchPopup().hide();
    }

    public void cancel(ActionEvent actionEvent) {
        try {
            ADFUtils.executeOperationBinding("Rollback"); 
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        this.getCreditUnionBranchPopup().hide();
    }
}
