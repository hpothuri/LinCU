package com.linCu.model.view;

import com.linCu.model.view.common.CreditUnionBranchVO;

import oracle.jbo.server.ViewObjectImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Fri Apr 14 17:59:25 IST 2017
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class CreditUnionBranchVOImpl extends ViewObjectImpl implements CreditUnionBranchVO {
    /**
     * This is the default constructor (do not remove).
     */
    public CreditUnionBranchVOImpl() {
    }
    
    public void createBranch(String user){
        CreditUnionBranchVORowImpl row = (CreditUnionBranchVORowImpl) this.createRow();
        if(row != null){
        this.insertRow(row);
        this.setCurrentRow(row);
        row.setCreatedBy(user);
        row.setLastUpdatedBy(user);
        }
    }
    
    public void updateBranch(String user){
        CreditUnionBranchVORowImpl row = (CreditUnionBranchVORowImpl) this.getCurrentRow();
        if(row != null){
        this.executeQuery();
        row.setLastUpdatedBy(user);
        long time = System.currentTimeMillis();
        java.sql.Timestamp timestamp = new java.sql.Timestamp(time);
        row.setLastUpdateDate(timestamp);
        }
    }
}

