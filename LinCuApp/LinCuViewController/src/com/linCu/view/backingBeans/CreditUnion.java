package com.linCu.view.backingBeans;

import com.linCu.view.utils.ADFUtils;

import javax.faces.event.ActionEvent;

import oracle.adf.view.rich.component.rich.RichPopup;

public class CreditUnion {
    private RichPopup createCreditUnionPopup;

    public CreditUnion() {
        super();
    }

    public void setCreateCreditUnionPopup(RichPopup createCreditUnionPopup) {
        this.createCreditUnionPopup = createCreditUnionPopup;
    }

    public RichPopup getCreateCreditUnionPopup() {
        return createCreditUnionPopup;
    }

    public String hideCreditUnionPopup() {
        this.getCreateCreditUnionPopup().hide();
        return null;
    }

    public void createCreditUnion(ActionEvent actionEvent) {
        try {
            ADFUtils.executeOperationBinding("CreateInsert"); 
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        this.getCreateCreditUnionPopup().show(hints);
    }

    public void editCreditUnion(ActionEvent actionEvent) {
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        this.getCreateCreditUnionPopup().show(hints);
    }

    public void save(ActionEvent actionEvent) {
        try {
            ADFUtils.executeOperationBinding("Commit"); 
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        this.getCreateCreditUnionPopup().hide();
    }

    public void cancel(ActionEvent actionEvent) {
        try {
            ADFUtils.executeOperationBinding("Rollback"); 
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        this.getCreateCreditUnionPopup().hide();
    }
}
