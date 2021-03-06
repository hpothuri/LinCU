package com.linCu.model.entity;

import java.math.BigDecimal;

import java.sql.Timestamp;

import oracle.jbo.AttributeList;
import oracle.jbo.Key;
import oracle.jbo.RowIterator;
import oracle.jbo.domain.DBSequence;
import oracle.jbo.server.EntityDefImpl;
import oracle.jbo.server.EntityImpl;
import oracle.jbo.server.SequenceImpl;
// ---------------------------------------------------------------------
// ---    File generated by Oracle ADF Business Components Design Time.
// ---    Sun Apr 16 23:09:46 IST 2017
// ---    Custom code may be added to this class.
// ---    Warning: Do not modify method signatures of generated methods.
// ---------------------------------------------------------------------
public class LincuMemberCardEOImpl extends EntityImpl {
    /**
     * Add attribute defaulting logic in this method.
     * @param attributeList list of attribute names/values to initialize the row
     */
    protected void create(AttributeList attributeList) {
        super.create(attributeList);
        //this.setCardId(new DBSequence((new SequenceImpl("employees_seq",getDBTransaction()).getSequenceNumber()).longValue()));
        this.setCardId(new BigDecimal((new SequenceImpl("CARD_ID_SEQ",getDBTransaction()).getSequenceNumber()).longValue()));
    }

    /**
     * AttributesEnum: generated enum for identifying attributes and accessors. DO NOT MODIFY.
     */
    public enum AttributesEnum {
        CardId,
        MemberId,
        CardReqType,
        CreditUnionId,
        CreatedBy,
        CreatedOn,
        SubmittedOn,
        AuthorizedBy,
        AuthorizedOn,
        CardStatus,
        Comments,
        BatchNumber,
        LincuComments,
        FcbComments,
        AdminComments,
        SupComments,
        RefCardId,
        TopupAmount,
        Active,
        ApplicationNo,
        CifNumber,
        MpsdId,
        LincuMemberInfoEO,
        LincuMemberKycEO,
        LincuMemberCardDocsEO,
        LincuMemberCardAuditEO,
        RefCardIdLincuMemberCardEO,
        LincuMemberCardEO;
        private static AttributesEnum[] vals = null;
        private static final int firstIndex = 0;

        public int index() {
            return AttributesEnum.firstIndex() + ordinal();
        }

        public static final int firstIndex() {
            return firstIndex;
        }

        public static int count() {
            return AttributesEnum.firstIndex() + AttributesEnum.staticValues().length;
        }

        public static final AttributesEnum[] staticValues() {
            if (vals == null) {
                vals = AttributesEnum.values();
            }
            return vals;
        }
    }


    public static final int CARDID = AttributesEnum.CardId.index();
    public static final int MEMBERID = AttributesEnum.MemberId.index();
    public static final int CARDREQTYPE = AttributesEnum.CardReqType.index();
    public static final int CREDITUNIONID = AttributesEnum.CreditUnionId.index();
    public static final int CREATEDBY = AttributesEnum.CreatedBy.index();
    public static final int CREATEDON = AttributesEnum.CreatedOn.index();
    public static final int SUBMITTEDON = AttributesEnum.SubmittedOn.index();
    public static final int AUTHORIZEDBY = AttributesEnum.AuthorizedBy.index();
    public static final int AUTHORIZEDON = AttributesEnum.AuthorizedOn.index();
    public static final int CARDSTATUS = AttributesEnum.CardStatus.index();
    public static final int COMMENTS = AttributesEnum.Comments.index();
    public static final int BATCHNUMBER = AttributesEnum.BatchNumber.index();
    public static final int LINCUCOMMENTS = AttributesEnum.LincuComments.index();
    public static final int FCBCOMMENTS = AttributesEnum.FcbComments.index();
    public static final int ADMINCOMMENTS = AttributesEnum.AdminComments.index();
    public static final int SUPCOMMENTS = AttributesEnum.SupComments.index();
    public static final int REFCARDID = AttributesEnum.RefCardId.index();
    public static final int TOPUPAMOUNT = AttributesEnum.TopupAmount.index();
    public static final int ACTIVE = AttributesEnum.Active.index();
    public static final int APPLICATIONNO = AttributesEnum.ApplicationNo.index();
    public static final int CIFNUMBER = AttributesEnum.CifNumber.index();
    public static final int MPSDID = AttributesEnum.MpsdId.index();
    public static final int LINCUMEMBERINFOEO = AttributesEnum.LincuMemberInfoEO.index();
    public static final int LINCUMEMBERKYCEO = AttributesEnum.LincuMemberKycEO.index();
    public static final int LINCUMEMBERCARDDOCSEO = AttributesEnum.LincuMemberCardDocsEO.index();
    public static final int LINCUMEMBERCARDAUDITEO = AttributesEnum.LincuMemberCardAuditEO.index();
    public static final int REFCARDIDLINCUMEMBERCARDEO = AttributesEnum.RefCardIdLincuMemberCardEO.index();
    public static final int LINCUMEMBERCARDEO = AttributesEnum.LincuMemberCardEO.index();

    /**
     * This is the default constructor (do not remove).
     */
    public LincuMemberCardEOImpl() {
    }

    /**
     * @return the definition object for this instance class.
     */
    public static synchronized EntityDefImpl getDefinitionObject() {
        return EntityDefImpl.findDefObject("com.linCu.model.entity.LincuMemberCardEO");
    }


    /**
     * Gets the attribute value for CardId, using the alias name CardId.
     * @return the value of CardId
     */
    public BigDecimal getCardId() {
        return (BigDecimal) getAttributeInternal(CARDID);
    }

    /**
     * Sets <code>value</code> as the attribute value for CardId.
     * @param value value to set the CardId
     */
    public void setCardId(BigDecimal value) {
        setAttributeInternal(CARDID, value);
    }

    /**
     * Gets the attribute value for MemberId, using the alias name MemberId.
     * @return the value of MemberId
     */
    public String getMemberId() {
        return (String) getAttributeInternal(MEMBERID);
    }

    /**
     * Sets <code>value</code> as the attribute value for MemberId.
     * @param value value to set the MemberId
     */
    public void setMemberId(String value) {
        setAttributeInternal(MEMBERID, value);
    }


    /**
     * Gets the attribute value for CardReqType, using the alias name CardReqType.
     * @return the value of CardReqType
     */
    public String getCardReqType() {
        return (String) getAttributeInternal(CARDREQTYPE);
    }

    /**
     * Sets <code>value</code> as the attribute value for CardReqType.
     * @param value value to set the CardReqType
     */
    public void setCardReqType(String value) {
        setAttributeInternal(CARDREQTYPE, value);
    }

    /**
     * Gets the attribute value for CreditUnionId, using the alias name CreditUnionId.
     * @return the value of CreditUnionId
     */
    public String getCreditUnionId() {
        return (String) getAttributeInternal(CREDITUNIONID);
    }

    /**
     * Sets <code>value</code> as the attribute value for CreditUnionId.
     * @param value value to set the CreditUnionId
     */
    public void setCreditUnionId(String value) {
        setAttributeInternal(CREDITUNIONID, value);
    }

    /**
     * Gets the attribute value for CreatedBy, using the alias name CreatedBy.
     * @return the value of CreatedBy
     */
    public String getCreatedBy() {
        return (String) getAttributeInternal(CREATEDBY);
    }

    /**
     * Sets <code>value</code> as the attribute value for CreatedBy.
     * @param value value to set the CreatedBy
     */
    public void setCreatedBy(String value) {
        setAttributeInternal(CREATEDBY, value);
    }

    /**
     * Gets the attribute value for CreatedOn, using the alias name CreatedOn.
     * @return the value of CreatedOn
     */
    public Timestamp getCreatedOn() {
        return (Timestamp) getAttributeInternal(CREATEDON);
    }

    /**
     * Sets <code>value</code> as the attribute value for CreatedOn.
     * @param value value to set the CreatedOn
     */
    public void setCreatedOn(Timestamp value) {
        setAttributeInternal(CREATEDON, value);
    }

    /**
     * Gets the attribute value for SubmittedOn, using the alias name SubmittedOn.
     * @return the value of SubmittedOn
     */
    public Timestamp getSubmittedOn() {
        return (Timestamp) getAttributeInternal(SUBMITTEDON);
    }

    /**
     * Sets <code>value</code> as the attribute value for SubmittedOn.
     * @param value value to set the SubmittedOn
     */
    public void setSubmittedOn(Timestamp value) {
        setAttributeInternal(SUBMITTEDON, value);
    }

    /**
     * Gets the attribute value for AuthorizedBy, using the alias name AuthorizedBy.
     * @return the value of AuthorizedBy
     */
    public String getAuthorizedBy() {
        return (String) getAttributeInternal(AUTHORIZEDBY);
    }

    /**
     * Sets <code>value</code> as the attribute value for AuthorizedBy.
     * @param value value to set the AuthorizedBy
     */
    public void setAuthorizedBy(String value) {
        setAttributeInternal(AUTHORIZEDBY, value);
    }

    /**
     * Gets the attribute value for AuthorizedOn, using the alias name AuthorizedOn.
     * @return the value of AuthorizedOn
     */
    public Timestamp getAuthorizedOn() {
        return (Timestamp) getAttributeInternal(AUTHORIZEDON);
    }

    /**
     * Sets <code>value</code> as the attribute value for AuthorizedOn.
     * @param value value to set the AuthorizedOn
     */
    public void setAuthorizedOn(Timestamp value) {
        setAttributeInternal(AUTHORIZEDON, value);
    }

    /**
     * Gets the attribute value for CardStatus, using the alias name CardStatus.
     * @return the value of CardStatus
     */
    public String getCardStatus() {
        return (String) getAttributeInternal(CARDSTATUS);
    }

    /**
     * Sets <code>value</code> as the attribute value for CardStatus.
     * @param value value to set the CardStatus
     */
    public void setCardStatus(String value) {
        setAttributeInternal(CARDSTATUS, value);
    }

    /**
     * Gets the attribute value for Comments, using the alias name Comments.
     * @return the value of Comments
     */
    public String getComments() {
        return (String) getAttributeInternal(COMMENTS);
    }

    /**
     * Sets <code>value</code> as the attribute value for Comments.
     * @param value value to set the Comments
     */
    public void setComments(String value) {
        setAttributeInternal(COMMENTS, value);
    }

    /**
     * Gets the attribute value for BatchNumber, using the alias name BatchNumber.
     * @return the value of BatchNumber
     */
    public BigDecimal getBatchNumber() {
        return (BigDecimal) getAttributeInternal(BATCHNUMBER);
    }

    /**
     * Sets <code>value</code> as the attribute value for BatchNumber.
     * @param value value to set the BatchNumber
     */
    public void setBatchNumber(BigDecimal value) {
        setAttributeInternal(BATCHNUMBER, value);
    }

    /**
     * Gets the attribute value for LincuComments, using the alias name LincuComments.
     * @return the value of LincuComments
     */
    public String getLincuComments() {
        return (String) getAttributeInternal(LINCUCOMMENTS);
    }

    /**
     * Sets <code>value</code> as the attribute value for LincuComments.
     * @param value value to set the LincuComments
     */
    public void setLincuComments(String value) {
        setAttributeInternal(LINCUCOMMENTS, value);
    }

    /**
     * Gets the attribute value for FcbComments, using the alias name FcbComments.
     * @return the value of FcbComments
     */
    public String getFcbComments() {
        return (String) getAttributeInternal(FCBCOMMENTS);
    }

    /**
     * Sets <code>value</code> as the attribute value for FcbComments.
     * @param value value to set the FcbComments
     */
    public void setFcbComments(String value) {
        setAttributeInternal(FCBCOMMENTS, value);
    }

    /**
     * Gets the attribute value for AdminComments, using the alias name AdminComments.
     * @return the value of AdminComments
     */
    public String getAdminComments() {
        return (String) getAttributeInternal(ADMINCOMMENTS);
    }

    /**
     * Sets <code>value</code> as the attribute value for AdminComments.
     * @param value value to set the AdminComments
     */
    public void setAdminComments(String value) {
        setAttributeInternal(ADMINCOMMENTS, value);
    }

    /**
     * Gets the attribute value for SupComments, using the alias name SupComments.
     * @return the value of SupComments
     */
    public String getSupComments() {
        return (String) getAttributeInternal(SUPCOMMENTS);
    }

    /**
     * Sets <code>value</code> as the attribute value for SupComments.
     * @param value value to set the SupComments
     */
    public void setSupComments(String value) {
        setAttributeInternal(SUPCOMMENTS, value);
    }


    /**
     * Gets the attribute value for RefCardId, using the alias name RefCardId.
     * @return the value of RefCardId
     */
    public BigDecimal getRefCardId() {
        return (BigDecimal) getAttributeInternal(REFCARDID);
    }

    /**
     * Sets <code>value</code> as the attribute value for RefCardId.
     * @param value value to set the RefCardId
     */
    public void setRefCardId(BigDecimal value) {
        setAttributeInternal(REFCARDID, value);
    }

    /**
     * Gets the attribute value for TopupAmount, using the alias name TopupAmount.
     * @return the value of TopupAmount
     */
    public BigDecimal getTopupAmount() {
        return (BigDecimal) getAttributeInternal(TOPUPAMOUNT);
    }

    /**
     * Sets <code>value</code> as the attribute value for TopupAmount.
     * @param value value to set the TopupAmount
     */
    public void setTopupAmount(BigDecimal value) {
        setAttributeInternal(TOPUPAMOUNT, value);
    }

    /**
     * Gets the attribute value for Active, using the alias name Active.
     * @return the value of Active
     */
    public String getActive() {
        return (String) getAttributeInternal(ACTIVE);
    }

    /**
     * Sets <code>value</code> as the attribute value for Active.
     * @param value value to set the Active
     */
    public void setActive(String value) {
        setAttributeInternal(ACTIVE, value);
    }

    /**
     * Gets the attribute value for ApplicationNo, using the alias name ApplicationNo.
     * @return the value of ApplicationNo
     */
    public String getApplicationNo() {
        return (String) getAttributeInternal(APPLICATIONNO);
    }

    /**
     * Sets <code>value</code> as the attribute value for ApplicationNo.
     * @param value value to set the ApplicationNo
     */
    public void setApplicationNo(String value) {
        setAttributeInternal(APPLICATIONNO, value);
    }

    /**
     * Gets the attribute value for CifNumber, using the alias name CifNumber.
     * @return the value of CifNumber
     */
    public String getCifNumber() {
        return (String) getAttributeInternal(CIFNUMBER);
    }

    /**
     * Sets <code>value</code> as the attribute value for CifNumber.
     * @param value value to set the CifNumber
     */
    public void setCifNumber(String value) {
        setAttributeInternal(CIFNUMBER, value);
    }

    /**
     * Gets the attribute value for MpsdId, using the alias name MpsdId.
     * @return the value of MpsdId
     */
    public String getMpsdId() {
        return (String) getAttributeInternal(MPSDID);
    }

    /**
     * Sets <code>value</code> as the attribute value for MpsdId.
     * @param value value to set the MpsdId
     */
    public void setMpsdId(String value) {
        setAttributeInternal(MPSDID, value);
    }

    /**
     * @return the associated entity oracle.jbo.RowIterator.
     */
    public RowIterator getLincuMemberInfoEO() {
        return (RowIterator) getAttributeInternal(LINCUMEMBERINFOEO);
    }

    /**
     * @return the associated entity oracle.jbo.RowIterator.
     */
    public RowIterator getLincuMemberKycEO() {
        return (RowIterator) getAttributeInternal(LINCUMEMBERKYCEO);
    }

    /**
     * @return the associated entity oracle.jbo.RowIterator.
     */
    public RowIterator getLincuMemberCardDocsEO() {
        return (RowIterator) getAttributeInternal(LINCUMEMBERCARDDOCSEO);
    }

    /**
     * @return the associated entity oracle.jbo.RowIterator.
     */
    public RowIterator getLincuMemberCardAuditEO() {
        return (RowIterator) getAttributeInternal(LINCUMEMBERCARDAUDITEO);
    }

    /**
     * @return the associated entity oracle.jbo.RowIterator.
     */
    public RowIterator getRefCardIdLincuMemberCardEO() {
        return (RowIterator) getAttributeInternal(REFCARDIDLINCUMEMBERCARDEO);
    }

    /**
     * @return the associated entity LincuMemberCardEOImpl.
     */
    public LincuMemberCardEOImpl getLincuMemberCardEO() {
        return (LincuMemberCardEOImpl) getAttributeInternal(LINCUMEMBERCARDEO);
    }

    /**
     * Sets <code>value</code> as the associated entity LincuMemberCardEOImpl.
     */
    public void setLincuMemberCardEO(LincuMemberCardEOImpl value) {
        setAttributeInternal(LINCUMEMBERCARDEO, value);
    }

    /**
     * @param cardId key constituent

     * @return a Key object based on given key constituents.
     */
    public static Key createPrimaryKey(BigDecimal cardId) {
        return new Key(new Object[] { cardId });
    }


}

