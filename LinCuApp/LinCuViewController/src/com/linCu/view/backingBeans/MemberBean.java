package com.linCu.view.backingBeans;

import com.linCu.view.utils.ADFUtils;

import com.linCu.view.utils.JSFUtils;

import com.linCu.view.utils.PasswordUtil;

import java.util.HashMap;
import java.util.Map;

import javax.faces.event.ActionEvent;

import javax.faces.event.ValueChangeEvent;

import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.context.AdfFacesContext;

public class MemberBean {
    private RichPopup confirmDeletePopup;
    private RichTable memberTable;

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
            ADFUtils.executeOperationBinding("closeMember"); 
            ADFUtils.executeOperationBinding("Commit"); 
            ADFUtils.executeOperationBinding("Execute"); 
            AdfFacesContext.getCurrentInstance().addPartialTarget(this.getMemberTable());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        this.getConfirmDeletePopup().hide();
    }

    public void onCopyOfPermanantAddress(ValueChangeEvent valueChangeEvent) {
        Map paramMap = new HashMap();
        paramMap.put("copy", valueChangeEvent.getNewValue());
        ADFUtils.executeOperationBinding("copyPermanantAddress",paramMap);
    }

    public void setMemberTable(RichTable memberTable) {
        this.memberTable = memberTable;
    }

    public RichTable getMemberTable() {
        return memberTable;
    }
}
