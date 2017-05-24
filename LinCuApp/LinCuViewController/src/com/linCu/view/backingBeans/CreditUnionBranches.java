package com.linCu.view.backingBeans;

import com.linCu.view.utils.ADFUtils;

import javax.faces.event.ActionEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.RichPopup;

import oracle.binding.BindingContainer;

import oracle.binding.OperationBinding;

import oracle.jbo.Key;

public class CreditUnionBranches {
    private RichPopup creditUnionBranchPopup;
    private RichPopup deleteBranchConfirmPopup;

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
            ADFUtils.executeOperationBinding("createBranch"); 
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
            ADFUtils.executeOperationBinding("updateBranch"); 
            ADFUtils.executeOperationBinding("Commit"); 
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        this.getCreditUnionBranchPopup().hide();
    }

    public void cancel(ActionEvent actionEvent) {
        try {
           DCBindingContainer dcBC = (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
           DCIteratorBinding dcIter = dcBC.findIteratorBinding("CreditUnionIterator");
            Key branchKey = dcIter.getCurrentRow().getKey();
            BindingContainer bc = BindingContext.getCurrent().getCurrentBindingsEntry();
            OperationBinding ob = bc.getOperationBinding("Rollback");
            ob.execute();
            dcIter.setCurrentRowWithKey(branchKey.toStringFormat(true));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        this.getCreditUnionBranchPopup().hide();
    }

    public void setDeleteBranchConfirmPopup(RichPopup deleteBranchConfirmPopup) {
        this.deleteBranchConfirmPopup = deleteBranchConfirmPopup;
    }

    public RichPopup getDeleteBranchConfirmPopup() {
        return deleteBranchConfirmPopup;
    }
}
