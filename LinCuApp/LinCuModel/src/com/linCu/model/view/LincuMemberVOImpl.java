package com.linCu.model.view;

import com.linCu.model.view.common.LincuMemberVO;

import oracle.jbo.server.ViewObjectImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Sun Mar 26 19:32:10 IST 2017
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class LincuMemberVOImpl extends ViewObjectImpl implements LincuMemberVO {
    /**
     * This is the default constructor (do not remove).
     */
    public LincuMemberVOImpl() {
    }
    
    public void createMember(String user){
        LincuMemberVORowImpl row = (LincuMemberVORowImpl) this.createRow();
        long time = System.currentTimeMillis();
        java.sql.Timestamp timestamp = new java.sql.Timestamp(time);
        if(row != null){
        this.insertRow(row);
        this.setCurrentRow(row);
        row.setCreatedBy(user);
        row.setCreationDate(timestamp);
        row.setLastUpdatedBy(user);
        row.setLastUpdateDate(timestamp);
        row.setCreatedBy1(user);
        row.setCreationDate1(timestamp);
        row.setLastUpdatedBy1(user);
        row.setLastUpdateDate1(timestamp);
        }
    }
    
    public void updateMember(String user){
        LincuMemberVORowImpl row = (LincuMemberVORowImpl) this.getCurrentRow();
        long time = System.currentTimeMillis();
        java.sql.Timestamp timestamp = new java.sql.Timestamp(time);
        if(row != null){
//            this.executeQuery();
//            this.setCurrentRow(row);
            row.setLastUpdatedBy(user);
            row.setLastUpdateDate(timestamp);
            row.setLastUpdatedBy1(user);
            row.setLastUpdateDate1(timestamp);
        }
    }
}

