package com.linCu.view.backingBeans;

import com.linCu.view.utils.ADFUtils;

import com.linCu.view.utils.JSFUtils;

import javax.faces.event.ActionEvent;

import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.nav.RichButton;
import oracle.adf.view.rich.context.AdfFacesContext;

public class CreditUnion {
    private RichPopup createCreditUnionPopup;
    private RichPopup deleteCreditUnionConfirmPopup;
    private RichTable creditUnionTable;
    private RichPopup deleteBranchConfirm;

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
            ADFUtils.executeOperationBinding("createCreditUnion"); 
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
            ADFUtils.executeOperationBinding("updateCreditUnion"); 
            ADFUtils.executeOperationBinding("Commit"); 
            ADFUtils.executeOperationBinding("Execute"); 
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

    public void confirmdeleteCreditUnion(ActionEvent actionEvent) {
        try {
            Boolean deleteCreditUnionAllowed = (Boolean)ADFUtils.executeOperationBinding("deleteCreditUnionAllowed");
            if(deleteCreditUnionAllowed){
                RichPopup.PopupHints hints = new RichPopup.PopupHints();
                this.getDeleteCreditUnionConfirmPopup().show(hints);   
            }else{
                JSFUtils.addErrorMessage("Cannot delete the credit union. It has branches associated to it.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setDeleteCreditUnionConfirmPopup(RichPopup deleteCreditUnionConfirmPopup) {
        this.deleteCreditUnionConfirmPopup = deleteCreditUnionConfirmPopup;
    }

    public RichPopup getDeleteCreditUnionConfirmPopup() {
        return deleteCreditUnionConfirmPopup;
    }

    public void deleteCreditUnion(ActionEvent actionEvent) {
        try {
            //ADFUtils.executeOperationBinding("Delete"); 
            ADFUtils.executeOperationBinding("closeCreditUnion");
            ADFUtils.executeOperationBinding("Commit"); 
            ADFUtils.executeOperationBinding("Execute"); 
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getCreditUnionTable());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        this.getDeleteCreditUnionConfirmPopup().hide();
    }

    public void setCreditUnionTable(RichTable creditUnionTable) {
        this.creditUnionTable = creditUnionTable;
    }

    public RichTable getCreditUnionTable() {
        return creditUnionTable;
    }

    public void setDeleteBranchConfirm(RichPopup deleteBranchConfirm) {
        this.deleteBranchConfirm = deleteBranchConfirm;
    }

    public RichPopup getDeleteBranchConfirm() {
        return deleteBranchConfirm;
    }
}
