package com.linCu.view.backingBeans;

import com.linCu.view.utils.ADFUtils;

import javax.faces.event.ActionEvent;

public class MemberBean {
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
}
