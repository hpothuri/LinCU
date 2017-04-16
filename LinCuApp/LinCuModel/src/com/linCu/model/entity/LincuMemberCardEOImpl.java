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
        MemberCardType,
        CardReqType,
        CreditUnionId,
        CreatedBy,
        CreatedOn,
        SubmittedOn,
        AuthorizedBy,
        AuthorizedOn,
        CardStatus,
        Comments,
        LincuMemberCardAuditEO,
        LincuMemberInfoEO,
        LincuMemberKycEO,
        LincuMemberCardDocsEO;
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
    public static final int MEMBERCARDTYPE = AttributesEnum.MemberCardType.index();
    public static final int CARDREQTYPE = AttributesEnum.CardReqType.index();
    public static final int CREDITUNIONID = AttributesEnum.CreditUnionId.index();
    public static final int CREATEDBY = AttributesEnum.CreatedBy.index();
    public static final int CREATEDON = AttributesEnum.CreatedOn.index();
    public static final int SUBMITTEDON = AttributesEnum.SubmittedOn.index();
    public static final int AUTHORIZEDBY = AttributesEnum.AuthorizedBy.index();
    public static final int AUTHORIZEDON = AttributesEnum.AuthorizedOn.index();
    public static final int CARDSTATUS = AttributesEnum.CardStatus.index();
    public static final int COMMENTS = AttributesEnum.Comments.index();
    public static final int LINCUMEMBERCARDAUDITEO = AttributesEnum.LincuMemberCardAuditEO.index();
    public static final int LINCUMEMBERINFOEO = AttributesEnum.LincuMemberInfoEO.index();
    public static final int LINCUMEMBERKYCEO = AttributesEnum.LincuMemberKycEO.index();
    public static final int LINCUMEMBERCARDDOCSEO = AttributesEnum.LincuMemberCardDocsEO.index();

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
     * Gets the attribute value for MemberCardType, using the alias name MemberCardType.
     * @return the value of MemberCardType
     */
    public String getMemberCardType() {
        return (String) getAttributeInternal(MEMBERCARDTYPE);
    }

    /**
     * Sets <code>value</code> as the attribute value for MemberCardType.
     * @param value value to set the MemberCardType
     */
    public void setMemberCardType(String value) {
        setAttributeInternal(MEMBERCARDTYPE, value);
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
     * @return the associated entity oracle.jbo.RowIterator.
     */
    public RowIterator getLincuMemberCardAuditEO() {
        return (RowIterator) getAttributeInternal(LINCUMEMBERCARDAUDITEO);
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
     * @param cardId key constituent

     * @return a Key object based on given key constituents.
     */
    public static Key createPrimaryKey(BigDecimal cardId) {
        return new Key(new Object[] { cardId });
    }


}

