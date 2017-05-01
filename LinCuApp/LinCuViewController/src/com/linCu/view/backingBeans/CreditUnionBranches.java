package com.linCu.view.backingBeans;

import com.linCu.view.utils.ADFUtils;

import javax.faces.event.ActionEvent;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.RichPopup;

import oracle.binding.BindingContainer;

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
            BindingContainer bindings = BindingContext.getCurrent().getCurrentBindingsEntry();
                 //Get Iterator of table
                 DCIteratorBinding parentIter = (DCIteratorBinding)bindings.get("IteratorName");
                 //Get current row key
                 Key parentKey = parentIter.getCurrentRow().getKey();

                 //You can add your operation code here, i have used simple Cancel operation 
                 //with Rollback and Execute
                 
                 
                 ADFUtils.executeOperationBinding("Rollback"); 
                 ADFUtils.executeOperationBinding("Execute"); 
                
                 //Set again row key as current row
                 parentIter.setCurrentRowWithKey(parentKey.toStringFormat(true));
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
