package com.linCu.view.backingBeans;

import com.linCu.view.utils.ADFUtils;

import java.util.HashMap;
import java.util.Map;

import javax.faces.event.ActionEvent;

import oracle.adf.view.rich.component.rich.RichPopup;

public class CardBean {
    private RichPopup commentsPopup;

    public CardBean() {
        super();
    }

    public void saveAndSubmit(ActionEvent actionEvent) {
        try {
             ADFUtils.executeOperationBinding("submitCard");
            ADFUtils.executeOperationBinding("Commit"); 
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void openPopup(ActionEvent actionEvent) {
        RichPopup.PopupHints hints = new RichPopup.PopupHints();
        this.getCommentsPopup().show(hints);
    }

    public void setCommentsPopup(RichPopup commentsPopup) {
        this.commentsPopup = commentsPopup;
    }

    public RichPopup getCommentsPopup() {
        return commentsPopup;
    }

    public void approve(ActionEvent actionEvent) {
        try {
            Map paramMap = new HashMap();
            paramMap.put("approver", "test");
            ADFUtils.executeOperationBinding("approve",paramMap);
            ADFUtils.executeOperationBinding("Commit");
            this.getCommentsPopup().hide();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
    }

    public void reject(ActionEvent actionEvent) {
        try {
            Map paramMap = new HashMap();
            paramMap.put("rejector", "test");
            ADFUtils.executeOperationBinding("reject",paramMap);
            ADFUtils.executeOperationBinding("Commit");
            this.getCommentsPopup().hide();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
